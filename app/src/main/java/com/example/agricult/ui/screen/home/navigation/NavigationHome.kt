package com.example.agricult.ui.screen.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.agricult.ui.screen.home.addAnnouncement.AddAnnouncement
import com.example.agricult.ui.screen.home.categories.Categories
import com.example.agricult.ui.screen.home.categories.category.CategoryScreen
import com.example.agricult.ui.screen.home.favorites.FavoritesScreen
import com.example.agricult.ui.screen.home.profileUser.ProfileUserScreen
import com.example.agricult.viewmodel.*

@Composable
fun NavigationHome(
    navHostController: NavHostController,
    profileRequestViewModel: ProfileRequestViewModel,
    roomViewModel: RoomViewModel,
    requestViewModel: RequestViewModel,
    navController: NavController,
    categoriesViewModel: CategoriesViewModel,
    categoryViewModel: CategoryViewModel
) {
    NavHost(navController = navHostController, startDestination = "categories") {
        composable("categories") {


            Categories(
                categoriesViewModel = categoriesViewModel,
                roomViewModel = roomViewModel,
                categoryViewModel = categoryViewModel,
                navHostController = navHostController
            )
        }
        composable("add_screen") {
            AddAnnouncement()
        }
        composable("favorites_screen") {
            FavoritesScreen()
        }

        composable("profile_user_screen") {
            ProfileUserScreen(
                profileRequestViewModel = profileRequestViewModel,
                roomViewModel = roomViewModel,
                requestViewModel = requestViewModel,
                navController = navController
            )
        }

        composable("category_screen") {
            CategoryScreen()
        }

    }
}