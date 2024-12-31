package com.lifestyle.mrcooper.navigation

sealed class Screen(val route: String) {
    data object Screen1 : Screen("screen1")
    data object Screen2 : Screen("screen2")
    data object Screen3 : Screen("screen3")
}