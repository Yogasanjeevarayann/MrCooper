package com.lifestyle.mrcooper.presentation.screen2


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifestyle.mrcooper.R
import com.lifestyle.mrcooper.data.db.PhoneEntity
import com.lifestyle.mrcooper.navigation.Screen
import com.lifestyle.mrcooper.presentation.common.CommonButton
import com.lifestyle.mrcooper.viewmodel.SharedViewModel

@Composable
fun Screen2(sharedViewModel: SharedViewModel, onNavigate: (Screen) -> Unit) {
    // Safely observe the phone numbers from the ViewModel
    val phoneNumbers = sharedViewModel.phoneNumbers.collectAsState().value

    // State to store the selected phone types for each number
    val selectedTypes = remember { mutableStateListOf<String?>(null, null, null, null) }
    val allPhoneTypes = listOf("Home", "Work", "Other")

    // Count the usage of each type
    val selectedTypeCounts = remember(selectedTypes) {
        derivedStateOf {
            selectedTypes.groupingBy { it }.eachCount().withDefault { 0 }
        }
    }
    val context = LocalContext.current
    if (phoneNumbers.isEmpty()) {
        // Show fallback UI when phone numbers are not available
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_phone_numbers),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        // UI to display phone numbers and dropdowns
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(phoneNumbers.size) { index ->
                    PhoneTypeRow(
                        phoneNumber = phoneNumbers[index],
                        selectedType = selectedTypes[index],
                        allPhoneTypes = allPhoneTypes,
                        selectedTypeCounts = selectedTypeCounts.value,
                        onTypeSelected = { selectedTypes[index] = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            val toastText = stringResource(id = R.string.toast_select_type)
            // Common UI elements
            CommonButton(
                onClick = {
                    if (selectedTypes.any { it == null }) {
                        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                    } else {
                        val entities = createPhoneEntities(phoneNumbers, selectedTypes)
                        sharedViewModel.savePhones(entities)
                        onNavigate(Screen.Screen3)
                    }
                },
                text = stringResource(id = R.string.save_button)
            )

        }
    }
}


@Composable
fun PhoneTypeRow(
    phoneNumber: String,
    selectedType: String?,
    allPhoneTypes: List<String>,
    selectedTypeCounts: Map<String?, Int>,
    onTypeSelected: (String) -> Unit
) {
    val availableTypes = allPhoneTypes.filter { type ->
        when (type) {
            "Home" -> (selectedTypeCounts["Home"] ?: 0) < 1 || selectedType == "Home"
            "Work" -> (selectedTypeCounts["Work"] ?: 0) < 1 || selectedType == "Work"
            "Other" -> (selectedTypeCounts["Other"] ?: 0) < 2 || selectedType == "Other"
            else -> true
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = phoneNumber,
            modifier = Modifier.weight(3f),
            style = MaterialTheme.typography.bodyLarge
        )
        DropdownSelector(
            selectedType = selectedType,
            availableTypes = availableTypes,
            onTypeSelected = onTypeSelected
        )
    }
}

@Composable
fun RowScope.DropdownSelector(
    selectedType: String?,
    availableTypes: List<String>,
    onTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.weight(2f)
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = selectedType ?: stringResource(id = R.string.select_type))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            availableTypes.forEach { type ->
                DropdownMenuItem(
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    },
                    text = { Text(type) }
                )
            }
        }
    }
}

fun createPhoneEntities(
    phoneNumbers: List<String>,
    selectedTypes: List<String?>
): List<PhoneEntity> {
    val otherCounter = mutableMapOf("Other" to 0)
    return phoneNumbers.mapIndexed { index, phone ->
        val phoneType = selectedTypes[index] ?: throw IllegalStateException("All types must be selected")

        // Dynamically assign "Other1" and "Other2"
        val finalPhoneType = if (phoneType == "Other") {
            val count = otherCounter["Other"] ?: 0
            otherCounter["Other"] = count + 1
            "Other${count + 1}"
        } else {
            phoneType
        }

        PhoneEntity(phoneNumber = phone, phoneType = finalPhoneType)
    }
}