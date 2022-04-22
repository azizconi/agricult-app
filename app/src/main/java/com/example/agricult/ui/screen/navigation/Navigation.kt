package com.example.agricult.ui.screen.navigation

import android.util.Log
import android.widget.Toast
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
    loginViewModel: LoginViewModel,
    roomViewModel: RoomViewModel,
    profileRequestViewModel: ProfileRequestViewModel,
    categoriesViewModel: CategoriesViewModel,
    categoryViewModel: CategoryViewModel,
    searchViewModel: SearchViewModel,
    dataStoreViewModel: DataStoreViewModel
) {
    val navController = rememberNavController()

    val context = LocalContext.current


    val isSuccessLoading = remember {
        mutableStateOf(false)
    }


    val getToken = dataStoreViewModel.readFromDataStore.observeAsState()



    Log.e("TAG", "Navigation: ${getToken.value}")


    isSuccessLoading.value = getToken.value != null


    val startDestination = if (isSuccessLoading.value) {
        Screen.HomeScreen.route
    } else {
        Screen.MainScreen.route
    }


    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.MainScreen.route) {

            MainScreen(navController = navController, loginViewModel = loginViewModel)

        }

        composable(route = Screen.LoginScreen.route) {
            if (isSuccessLoading.value) {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0)
                }
            } else {
                LoginScreen(
                    navController = navController,
                    loginViewModel = loginViewModel,
                    roomViewModel = roomViewModel,
                    dataStoreViewModel = dataStoreViewModel
                ) {

//                    isSuccessLoading.value = it


                }


            }

        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, loginViewModel = loginViewModel)
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                profileRequestViewModel = profileRequestViewModel,
                roomViewModel = roomViewModel,
                loginViewModel = loginViewModel,
                navController = navController,
                categoriesViewModel = categoriesViewModel,
                categoryViewModel = categoryViewModel,
                searchViewModel = searchViewModel,
                dataStoreViewModel = dataStoreViewModel
            )

        }


    }

}


