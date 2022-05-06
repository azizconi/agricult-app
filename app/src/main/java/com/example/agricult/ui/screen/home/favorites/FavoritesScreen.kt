package com.example.agricult.ui.screen.home.favorites

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.agricult.R
import com.example.agricult.ui.screen.ErrorInternetConnection
import com.example.agricult.ui.screen.home.announcementItem.AnnouncementItemScreen
import com.example.agricult.ui.screen.home.categories.SearchBar
import com.example.agricult.ui.screen.home.categories.category.getPhotoPagination
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.CategoryViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.FavouriteViewModel


@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    dataStoreViewModel: DataStoreViewModel,
    favouriteViewModel: FavouriteViewModel,
    navHostController: NavHostController,
    categoryViewModel: CategoryViewModel
) {
    val isLock = remember {
        mutableStateOf(true)
    }

//    if (isLock.value) {

    val getToken = remember {
        mutableStateOf("")
    }

    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()



    favouriteViewModel.getFavouritesAnnouncement(
        token = getToken.value,
        dataStoreViewModel = dataStoreViewModel
    )


    isLock.value = false
//    }

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Избранное",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color.White
                )
            },
            backgroundColor = PrimaryColorGreen,
            modifier = modifier
                .height(56.dp)
                .fillMaxWidth()
        )


        val data = getPhotoPagination(
            dataStoreViewModel = dataStoreViewModel,
            categoryViewModel = categoryViewModel
        ).collectAsLazyPagingItems()



//        if (favouriteViewModel.isSuccessLoadingFavorites.value) {
        AnnouncementItemScreen(
            categoryModel = favouriteViewModel.getFavouriteAnnouncementModel.value,
            dataStoreViewModel = dataStoreViewModel,
            favouriteViewModel = favouriteViewModel,
            navHostController = navHostController,
            categoryViewModel = categoryViewModel,
            data = data
        )

//        } else {
//            ErrorInternetConnection(
//                isLock = isLock.value,
//
//                ) {
//                isLock.value = !isLock.value
    }
//        }


//    }

}

