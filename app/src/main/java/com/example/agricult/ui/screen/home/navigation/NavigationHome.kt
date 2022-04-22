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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NavigationHome(
    navHostController: NavHostController,
    profileRequestViewModel: ProfileRequestViewModel,
    roomViewModel: RoomViewModel,
    loginViewModel: LoginViewModel,
    navController: NavController,
    categoriesViewModel: CategoriesViewModel,
    categoryViewModel: CategoryViewModel,
    searchViewModel: SearchViewModel,
    dataStoreViewModel: DataStoreViewModel
) {
    NavHost(navController = navHostController, startDestination = "categories") {
        composable("categories") {


            Categories(
                categoriesViewModel = categoriesViewModel,
                roomViewModel = roomViewModel,
                categoryViewModel = categoryViewModel,
                navHostController = navHostController,
                searchViewModel = searchViewModel,
                dataStoreViewModel = dataStoreViewModel
            )
        }
        composable("add_screen") {
            AddAnnouncement(dataStoreViewModel = dataStoreViewModel)
        }
        composable("favorites_screen") {
            FavoritesScreen(dataStoreViewModel = dataStoreViewModel)
        }

        composable("profile_user_screen") {
            ProfileUserScreen(
                profileRequestViewModel = profileRequestViewModel,
                roomViewModel = roomViewModel,
                loginViewModel = loginViewModel,
                navController = navController,
                dataStoreViewModel = dataStoreViewModel
            )
        }

        composable(
            "category_screen?id={id}",
        ) {
//            URLEncoder.encode("category_screen/{id}", StandardCharsets.UTF_8.toString())


            val id = it.arguments?.getString("id")

            CategoryScreen(
                idCategory = id!!,
                categoryViewModel = categoryViewModel,
                dataStoreViewModel = dataStoreViewModel
            )
        }

    }
}