package com.example.agricult.ui.screen.home.userAds

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.models.category.Data
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextPalleteGrey
import com.example.agricult.viewmodel.CategoryViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.FavouriteViewModel
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun UserAdsScreen(
    navHostController: NavHostController,
    dataStoreViewModel: DataStoreViewModel,
    favouriteViewModel: FavouriteViewModel,
    categoryViewModel: CategoryViewModel
) {

    val getToken = remember {
        mutableStateOf("")
    }

    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()


//    categoryViewModel.getCategoryModel.value = emptyList()

    categoryViewModel.userAdsRequest(
        getToken.value,
        dataStoreViewModel = dataStoreViewModel
    )

    val getUserAds = categoryViewModel.getCategoryModel.value

    Log.e("TAG", "UserAdsScreen: $getUserAds", )
    Column {
        UserAdsToolbar(navHostController = navHostController)
        AnnouncementUserAdsItemsScreen(
            categoryModel = getUserAds,
            dataStoreViewModel = dataStoreViewModel,
            favouriteViewModel = favouriteViewModel,
            navHostController = navHostController,
            categoryViewModel = categoryViewModel
        )
    }
}

@Composable
fun UserAdsToolbar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {

    TopAppBar(
        title = {
            Text(
                text = "Мои объявления",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(
                modifier = modifier,
                onClick = {
                    navHostController.popBackStack()
                }
            ) {

                Icon(
                    rememberCoilPainter(request = R.drawable.back),
                    contentDescription = "menu",
                    modifier = modifier
                        .width(16.dp)
                        .height(15.56.dp),
                    tint = Color.White

                )
            }
        },
        backgroundColor = PrimaryColorGreen,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
    )

}


@Composable
fun AnnouncementUserAdsItemsScreen(
    modifier: Modifier = Modifier,
    categoryModel: List<Data>,
    dataStoreViewModel: DataStoreViewModel,
    favouriteViewModel: FavouriteViewModel,
    categoryViewModel: CategoryViewModel,
    navHostController: NavHostController
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xffE5E5E5))
            .padding(bottom = 60.dp),

        ) {
        items(categoryModel.size) {

            when (categoryModel[it].moderation_status.id) {
                2 -> {
                    AnnouncementUserAdsItems(
                        data = categoryModel[it],
                        dataStoreViewModel = dataStoreViewModel,
                        favouriteViewModel = favouriteViewModel,
                        categoryViewModel = categoryViewModel,
                        navHostController = navHostController
                    )
                }
                1 -> {
                    AnnouncementDeniedUserAdsScreen(
                        data = categoryModel[it],
                        dataStoreViewModel = dataStoreViewModel,
                        favouriteViewModel = favouriteViewModel,
                        categoryViewModel = categoryViewModel,
                        navHostController = navHostController
                    )
                }
                else -> {
                    AnnouncementUnderConsiderationUserAdsScreen(
                        data = categoryModel[it],
                        dataStoreViewModel = dataStoreViewModel,
                        favouriteViewModel = favouriteViewModel,
                        categoryViewModel = categoryViewModel,
                        navHostController = navHostController
                    )
                }
            }

        }
    }
}


@Composable
fun AnnouncementUserAdsItems(
    modifier: Modifier = Modifier,
    data: Data,
    favouriteViewModel: FavouriteViewModel,
    dataStoreViewModel: DataStoreViewModel,
    categoryViewModel: CategoryViewModel,
    navHostController: NavHostController
) {

    val isFavouriteIcon = remember {
        mutableStateOf(R.drawable.heart)
    }


    if (!data.is_favorite) {
        isFavouriteIcon.value = R.drawable.heart
    } else {
        isFavouriteIcon.value = R.drawable.heard_click
    }

    val getToken = remember {
        mutableStateOf("")
    }

    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()


    Row(


        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 16.dp)
            .clickable {
                categoryViewModel.getMyAdsModel = null
                categoryViewModel.getMyAds(
                    getToken.value,
                    id = data.id,
                    dataStoreViewModel = dataStoreViewModel
                )


                navHostController.navigate("edit_my_ads")


            }
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(4.dp),


            ) {

            Column(
                modifier = modifier
                    .padding(12.dp)
                    .height(124.dp)
                    .width(343.dp)
            ) {
                Row {

                    Image(
                        painter = rememberCoilPainter(request = "http://api.agricult.colibri.tj/public/storage/${data.images}"),
                        contentDescription = "image announcement",
                        modifier = modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                    )

                    Column(
                        modifier = modifier
                            .width(191.dp)
                            .height(100.dp)
                            .padding(start = 12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = data.title,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium))
                        )


                        Column {
                            Text(
                                text = "Стоимость:",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                color = TextPalleteGrey
                            )

                            Text(
                                text = data.price.toString(),
                                color = PrimaryColorGreen,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontSize = 15.sp
                            )

                        }

                    }

                    Column(
                        modifier = modifier
                            .padding(start = 14.dp, top = 5.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (!data.is_favorite) {
                                    favouriteViewModel.favoritesStore(
                                        getToken.value,
                                        data.id
                                    )
                                    data.is_favorite = true
                                } else {
                                    favouriteViewModel.deleteFavoriteStore(
                                        token = getToken.value,
                                        data.id
                                    )
                                    data.is_favorite = false
                                }

                                if (!data.is_favorite) {
                                    isFavouriteIcon.value = R.drawable.heart
                                } else {
                                    isFavouriteIcon.value = R.drawable.heard_click
                                }

                            },
                            modifier = modifier
                                .width(22.dp)
                                .height(20.dp)

                        ) {
                            Image(
                                painter = rememberCoilPainter(request = isFavouriteIcon.value),
                                contentDescription = "favorite",
                                modifier = modifier
                                    .width(20.dp)
                                    .height(18.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AnnouncementDeniedUserAdsScreen(
    modifier: Modifier = Modifier,
    data: Data,
    favouriteViewModel: FavouriteViewModel,
    dataStoreViewModel: DataStoreViewModel,
    categoryViewModel: CategoryViewModel,
    navHostController: NavHostController
) {
    val isFavouriteIcon = remember {
        mutableStateOf(R.drawable.heart)
    }


    if (!data.is_favorite) {
        isFavouriteIcon.value = R.drawable.heart
    } else {
        isFavouriteIcon.value = R.drawable.heard_click
    }

    val isClickFavourite = remember {
        mutableStateOf(false)
    }

    val getToken = remember {
        mutableStateOf("")
    }

    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()


    Row(


        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 16.dp)
            .clickable {
                categoryViewModel.getMyAdsModel = null
                categoryViewModel.getMyAds(
                    getToken.value,
                    id = data.id,
                    dataStoreViewModel = dataStoreViewModel
                )
                navHostController.navigate("edit_my_ads")
            }

    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(4.dp),


            ) {

            Column(
                modifier = modifier
                    .padding(12.dp)
                    .height(124.dp)
                    .width(343.dp)
            ) {
                Row {

                    Image(
                        painter = rememberCoilPainter(request = "http://api.agricult.colibri.tj/public/storage/${data.images}"),
                        contentDescription = "image announcement",
                        modifier = modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                    )

                    Column(
                        modifier = modifier
                            .width(191.dp)
                            .height(100.dp)
                            .padding(start = 12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = data.title,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium))
                        )


                        Column {
                            Text(
                                text = "Статус:",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                color = TextPalleteGrey
                            )

                            Text(
                                text = data.moderation_status.name,
                                color = Color(0xff999999),
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontSize = 15.sp
                            )

                        }

                    }

                    Column(
                        modifier = modifier
                            .padding(start = 14.dp, top = 5.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (!data.is_favorite) {
                                    favouriteViewModel.favoritesStore(
                                        getToken.value,
                                        data.id
                                    )
                                    data.is_favorite = true
                                } else {
                                    favouriteViewModel.deleteFavoriteStore(
                                        token = getToken.value,
                                        data.id
                                    )
                                    data.is_favorite = false
                                }

                                if (!data.is_favorite) {
                                    isFavouriteIcon.value = R.drawable.heart
                                } else {
                                    isFavouriteIcon.value = R.drawable.heard_click
                                }

                            },
                            modifier = modifier
                                .width(22.dp)
                                .height(20.dp)

                        ) {
                            Image(
                                painter = rememberCoilPainter(request = isFavouriteIcon.value),
                                contentDescription = "favorite",
                                modifier = modifier
                                    .width(20.dp)
                                    .height(18.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnnouncementUnderConsiderationUserAdsScreen(
    modifier: Modifier = Modifier,
    data: Data,
    favouriteViewModel: FavouriteViewModel,
    dataStoreViewModel: DataStoreViewModel,
    categoryViewModel: CategoryViewModel,
    navHostController: NavHostController
) {
    val isFavouriteIcon = remember {
        mutableStateOf(R.drawable.heart)
    }


    if (!data.is_favorite) {
        isFavouriteIcon.value = R.drawable.heart
    } else {
        isFavouriteIcon.value = R.drawable.heard_click
    }

    val isClickFavourite = remember {
        mutableStateOf(false)
    }

    val getToken = remember {
        mutableStateOf("")
    }

    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()


    Row(


        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 16.dp)
            .clickable {
                categoryViewModel.getMyAdsModel = null
                categoryViewModel.getMyAds(
                    getToken.value,
                    id = data.id,
                    dataStoreViewModel = dataStoreViewModel
                )
                navHostController.navigate("edit_my_ads")
            }

    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(4.dp),


            ) {

            Column(
                modifier = modifier
                    .padding(12.dp)
                    .height(124.dp)
                    .width(343.dp)
            ) {
                Row {

                    Image(
                        painter = rememberCoilPainter(request = "http://api.agricult.colibri.tj/public/storage/${data.images}"),
                        contentDescription = "image announcement",
                        modifier = modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(shape = RoundedCornerShape(4.dp)),
                    )

                    Column(
                        modifier = modifier
                            .width(191.dp)
                            .height(100.dp)
                            .padding(start = 12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = data.title,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium))
                        )


                        Column {
                            Text(
                                text = "Статус:",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                color = TextPalleteGrey
                            )

                            Text(
                                text = data.moderation_status.name,
                                color = Color.Red,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontSize = 15.sp
                            )

                        }

                    }

                    Column(
                        modifier = modifier
                            .padding(start = 14.dp, top = 5.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (!data.is_favorite) {
                                    favouriteViewModel.favoritesStore(
                                        getToken.value,
                                        data.id
                                    )
                                    data.is_favorite = true
                                } else {
                                    favouriteViewModel.deleteFavoriteStore(
                                        token = getToken.value,
                                        data.id
                                    )
                                    data.is_favorite = false
                                }

                                if (!data.is_favorite) {
                                    isFavouriteIcon.value = R.drawable.heart
                                } else {
                                    isFavouriteIcon.value = R.drawable.heard_click
                                }

                            },
                            modifier = modifier
                                .width(22.dp)
                                .height(20.dp)

                        ) {
                            Image(
                                painter = rememberCoilPainter(request = isFavouriteIcon.value),
                                contentDescription = "favorite",
                                modifier = modifier
                                    .width(20.dp)
                                    .height(18.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
