package com.example.agricult.ui.screen.navigation

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object HomeScreen: Screen("home_screen")

    companion object {
        fun getStartDestination() = MainScreen.route
    }
}
