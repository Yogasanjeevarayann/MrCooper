package com.lifestyle.mrcooper.presentation.screen1

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifestyle.mrcooper.R
import com.lifestyle.mrcooper.navigation.Screen
import com.lifestyle.mrcooper.util.AppLogger
import com.lifestyle.mrcooper.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.filter

@Composable
fun Screen1(sharedViewModel: SharedViewModel, onNavigate: (Screen) -> Unit) {
    // Observe the latest state from ViewModel
    val fetchedPhoneNumbers by sharedViewModel.phoneNumbers.collectAsState()
    val isLoading by sharedViewModel.isLoading.collectAsState()
    sharedViewModel.clearDatabase(){
        AppLogger.logDebug("Database cleared in for one more confirmation")
    }
    // Navigate to Screen2 when 4 or more phone numbers are fetched
    LaunchedEffect(Unit) {
        snapshotFlow { fetchedPhoneNumbers.size >= 4 }
            .filter { it } // Trigger only when the condition is true
            .collect {
                AppLogger.logDebug("Navigating to Screen2 with phone numbers: $fetchedPhoneNumbers")
                onNavigate(Screen.Screen2) // Perform navigation
            }
    }

    Column(
        modifier = Modifier.fillMaxSize(), // Fill the available screen space
        verticalArrangement = Arrangement.Center, // Vertically center content
        horizontalAlignment = Alignment.CenterHorizontally // Horizontally center content
    ) {
        Button(
            onClick = { sharedViewModel.fetchPhoneNumbers() }, // Trigger data fetch
            enabled = !isLoading, // Disable button while loading
            modifier = Modifier
                .height(60.dp)
                .width(150.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp), // Show spinner when loading
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = stringResource(id = R.string.start_button_text), style = MaterialTheme.typography.titleMedium) // Show text when idle
            }
        }
    }
}
