package com.example.agricult.ui.screen.home.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.agricult.models.addAds.AddAds
import com.example.agricult.ui.screen.home.addAnnouncement.AddAnnouncement
import com.example.agricult.ui.screen.home.addAnnouncement.AddAnnouncementSecondScreen
import com.example.agricult.ui.screen.home.categories.Categories
import com.example.agricult.ui.screen.home.categories.category.CategoryScreen
import com.example.agricult.ui.screen.home.contact.ContactScreen
import com.example.agricult.ui.screen.home.favorites.FavoritesScreen
import com.example.agricult.ui.screen.home.profileUser.ProfileUserScreen
import com.example.agricult.ui.screen.home.search.SearchScreen
import com.example.agricult.ui.screen.home.user_info.UserInfoScreen
import com.example.agricult.viewmodel.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NavigationHome(
    navHostController: NavHostController,
    profileRequestViewModel: ProfileRequestViewModel,
    loginViewModel: LoginViewModel,
    navController: NavController,
    categoriesViewModel: CategoriesViewModel,
    categoryViewModel: CategoryViewModel,
    searchViewModel: SearchViewModel,
    dataStoreViewModel: DataStoreViewModel,
    favouriteViewModel: FavouriteViewModel,
    contactViewModel: ContactViewModel
) {
    NavHost(navController = navHostController, startDestination = "categories") {
        composable("categories") {


            Categories(
                categoriesViewModel = categoriesViewModel,
                categoryViewModel = categoryViewModel,
                navHostController = navHostController,
                searchViewModel = searchViewModel,
                dataStoreViewModel = dataStoreViewModel
            ) {

            }
        }
        composable("add_screen") {
            AddAnnouncement(
                navHostController = navHostController,
                categoriesViewModel = categoriesViewModel,
                profileRequestViewModel = profileRequestViewModel,
                dataStoreViewModel = dataStoreViewModel
            )
        }
        composable("favorites_screen") {
            FavoritesScreen(
                dataStoreViewModel = dataStoreViewModel,
                favouriteViewModel = favouriteViewModel
            )
        }

        composable("profile_user_screen") {
            ProfileUserScreen(
                profileRequestViewModel = profileRequestViewModel,
                loginViewModel = loginViewModel,
                navHostController = navHostController,
                dataStoreViewModel = dataStoreViewModel
            )
        }

        composable(
            "category_screen?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {

            val id = it.arguments?.getString("id")

            CategoryScreen(
                idCategory = id!!,
                categoryViewModel = categoryViewModel,
                dataStoreViewModel = dataStoreViewModel,
                favouriteViewModel = favouriteViewModel,
                searchViewModel = searchViewModel,
                navHostController = navHostController
            )
        }

        composable(
            "search_screen?query={query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ) {

            val query = it.arguments?.getString("query")

            SearchScreen(
                dataStoreViewModel = dataStoreViewModel,
                searchViewModel = searchViewModel,
                favouriteViewModel = favouriteViewModel,
                navHostController = navHostController,
                query = query.toString()
            )
        }


        composable("addAdsSecondScreen?data={data}") {
            val data =
                navHostController.previousBackStackEntry?.arguments?.getParcelable<AddAds>("data")


            Log.e("TAG", "NavigationHome: $data")

            AddAnnouncementSecondScreen(data = data, navHostController = navHostController)

        }


        composable("contact_screen") {
            ContactScreen(
                navHostController = navHostController,
                contactViewModel = contactViewModel,
                dataStoreViewModel = dataStoreViewModel
            )
        }

        composable("user_info_screen") {
            UserInfoScreen(
                navHostController = navHostController,
                dataStoreViewModel = dataStoreViewModel,
                profileRequestViewModel = profileRequestViewModel
            )
        }

    }

}