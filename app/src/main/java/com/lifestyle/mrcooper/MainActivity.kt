package com.lifestyle.mrcooper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.lifestyle.mrcooper.navigation.AppNavHost
import com.lifestyle.mrcooper.viewmodel.SharedViewModel
import com.lifestyle.mrcooper.ui.theme.MrCooperTheme
import com.lifestyle.mrcooper.util.AppLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition { sharedViewModel.isSplashLoading.value }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MrCooperTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        sharedViewModel = hiltViewModel(),
                        innerPadding = innerPadding
                    )
                }
            }

        }
    }

    override fun onDestroy() {
        AppLogger.logDebug("OnDestroy Called")
        sharedViewModel.clearDatabaseBlocking()
        super.onDestroy()
    }
}
