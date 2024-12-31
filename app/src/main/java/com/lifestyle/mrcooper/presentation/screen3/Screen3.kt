package com.lifestyle.mrcooper.presentation.screen3

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifestyle.mrcooper.R
import com.lifestyle.mrcooper.data.db.PhoneEntity
import com.lifestyle.mrcooper.presentation.common.CommonButton
import com.lifestyle.mrcooper.viewmodel.SharedViewModel

@Composable
fun Screen3(sharedViewModel: SharedViewModel) {
    val phones by sharedViewModel.phones.collectAsState()
    var showExitDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Handle back press
    BackHandler(enabled = true) {
        showExitDialog = true
    }

    // Confirmation Dialog
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(stringResource(id = R.string.exit_app_title)) },
            text = { Text(stringResource(id = R.string.exit_app_message)) },
            confirmButton = {
                TextButton(onClick = {
                    sharedViewModel.clearDatabase {
                        showExitDialog = false
                        exitApp(context)
                    }
                }) {
                    Text(stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(stringResource(id = R.string.no))
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Common UI elements
        CommonButton(
            onClick = { sharedViewModel.loadPhones() },
            text = stringResource(id = R.string.show_button_text),
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(phones.size) { index ->
                PhoneRow(phones[index])
            }
        }
    }
}


@Composable
fun PhoneRow(phoneEntity: PhoneEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = phoneEntity.phoneNumber,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f) // Left-aligned
        )
        Text(
            text = phoneEntity.phoneType,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End // Right-aligned
        )
    }
}


fun exitApp(context: Context) {
    if (context is Activity) {
        context.finish() // Finish the activity properly
    }
}
