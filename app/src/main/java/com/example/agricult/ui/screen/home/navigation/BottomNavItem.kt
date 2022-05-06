package com.example.agricult.ui.screen.home.navigation

data class BottomNavItem(
    val name: String,
    val route: String,
    val routesIncluded: List<String>,
    val icon: Int,
    val badgeCount: Int = 0,

)




