package com.example.agricult.ui.screen.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agricult.ui.screen.home.HomeScreen
import com.example.agricult.ui.screen.login.LoginScreen
import com.example.agricult.ui.screen.main.MainScreen
import com.example.agricult.ui.screen.register.RegisterScreen
import com.example.agricult.viewmodel.*


@Composable
fun Navigation(
    requestViewModel: RequestViewModel,
    roomViewModel: RoomViewModel,
    profileRequestViewModel: ProfileRequestViewModel,
    categoriesViewModel: CategoriesViewModel,
    categoryViewModel: CategoryViewModel
) {
    val navController = rememberNavController()

    val isSuccessLoading = remember {
        mutableStateOf(false)
    }

    val getTokenList = roomViewModel.getToken().observeAsState()
    getTokenList.value?.forEach {


        if (it.accessToken == null) {
            isSuccessLoading.value = false
        } else {
            isSuccessLoading.value = it.isSuccessLoading
        }

    }


    val startDestination = if (isSuccessLoading.value) {
        Screen.HomeScreen.route
    } else {
        Screen.MainScreen.route
    }


    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.MainScreen.route) {

            MainScreen(navController = navController, requestViewModel = requestViewModel)

        }

        composable(route = Screen.LoginScreen.route) {
            if (isSuccessLoading.value) {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0)
                }
            } else {
                LoginScreen(
                    navController = navController,
                    requestViewModel = requestViewModel,
                    roomViewModel = roomViewModel
                ) {

                    isSuccessLoading.value = it


                }


            }

        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, requestViewModel = requestViewModel)
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                profileRequestViewModel = profileRequestViewModel,
                roomViewModel = roomViewModel,
                requestViewModel = requestViewModel,
                navController = navController,
                categoriesViewModel = categoriesViewModel,
                categoryViewModel = categoryViewModel
            )

        }


    }

}


