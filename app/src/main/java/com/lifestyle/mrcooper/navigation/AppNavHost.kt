package com.lifestyle.mrcooper.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lifestyle.mrcooper.presentation.screen1.Screen1
import com.lifestyle.mrcooper.presentation.screen2.Screen2
import com.lifestyle.mrcooper.viewmodel.SharedViewModel
import com.lifestyle.mrcooper.presentation.screen3.Screen3

@Composable
fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel, innerPadding: PaddingValues) {

    val commonEnterTransition: EnterTransition =
        fadeIn(animationSpec = tween(600, easing = LinearEasing)) +
                slideInHorizontally(
                    initialOffsetX = { it }, // Slides in from the right
                    animationSpec = tween(600, easing = EaseIn)
                )

    val commonExitTransition: ExitTransition = fadeOut(animationSpec = tween(400, easing = LinearEasing))


    NavHost(
        navController = navController,
        startDestination = Screen.Screen1.route,
        modifier = Modifier.padding(innerPadding) // Handle padding for content
    ) {
        // Screen1 Configuration
        composable(
            route = Screen.Screen1.route,
            enterTransition = { commonEnterTransition },
            exitTransition = { commonExitTransition }
        ) {
            Screen1(sharedViewModel) { nextScreen ->
                navController.navigate(nextScreen.route) {
                    navController.popBackStack() // Clear previous stack
                }
            }
        }

        // Screen2 Configuration
        composable(
            route = Screen.Screen2.route,
            enterTransition = { commonEnterTransition },
            exitTransition = { commonExitTransition }
        ) {
            Screen2(sharedViewModel) { nextScreen ->
                navController.navigate(nextScreen.route) {
                    navController.popBackStack()
                }
            }
        }

        // Screen3 Configuration
        composable(
            route = Screen.Screen3.route,
            enterTransition = { commonEnterTransition },
            exitTransition = { commonExitTransition }
        ) {
            Screen3(sharedViewModel)
        }
    }
}
