package com.example.agricult.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.agricult.R
import com.example.agricult.ui.screen.home.navigation.BottomNavItem
import com.example.agricult.ui.screen.home.navigation.NavigationHome
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.*


@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
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
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Объявления",
                        route = "categories",
                        icon = R.drawable.list,
                        routesIncluded = listOf(
                            "category_screen?id={id}",
                            "search_screen?query={query}",
                            "ads_screen"
                        )
                    ),
                    BottomNavItem(
                        name = "Подать объявления",
                        route = "add_screen",
                        icon = R.drawable.add,
                        badgeCount = 0,
                        routesIncluded = listOf(
                            "addAdsSecondScreen?price={price}&" +
                                    "address={address}&" +
                                    "title={title}&" +
                                    "phone={phone}&" +
                                    "email={email}&" +
                                    "categoryId={categoryId}&" +
                                    "userId={userId}"
                        )
                    ),
                    BottomNavItem(
                        name = "Избранное",
                        route = "favorites_screen",
                        icon = R.drawable.heart,
                        badgeCount = 0,
                        routesIncluded = listOf()
                    ),
                    BottomNavItem(
                        name = "Личный кабинет",
                        route = "profile_user_screen",
                        icon = R.drawable.profile,
                        badgeCount = 0,
                        routesIncluded = listOf(
                            "contact_screen",
                            "user_info_screen",
                            "user_ads_screen",
                            "edit_my_ads"
                        )
                    ),
                ),
                navController = navHostController,
                onItemClick = {
                    navHostController.navigate(it.route) {
                        popUpTo(route = it.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    ) {
        NavigationHome(
            navController = navController,
            profileRequestViewModel = profileRequestViewModel,
            loginViewModel = loginViewModel,
            navHostController = navHostController,
            categoriesViewModel = categoriesViewModel,
            categoryViewModel = categoryViewModel,
            searchViewModel = searchViewModel,
            dataStoreViewModel = dataStoreViewModel,
            favouriteViewModel = favouriteViewModel,
            contactViewModel = contactViewModel
        )
    }
}


@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()


    BottomNavigation(
        modifier = modifier
            .height(56.dp),
        backgroundColor = Color.White,

        ) {


        items.forEach { item ->


            val selected = item.route == backStackEntry.value?.destination?.route ||
                    item.routesIncluded.any {
                        it == backStackEntry.value?.destination?.route
                    }



            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = PrimaryColorGreen,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {

                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.name,
                            modifier = modifier
                                .size(25.dp)
                                .padding(bottom = 4.dp)
                        )


                        Text(
                            text = item.name,
                            textAlign = TextAlign.Center,
                            fontSize = 9.sp
                        )

                    }
                }
            )
        }
    }
}


