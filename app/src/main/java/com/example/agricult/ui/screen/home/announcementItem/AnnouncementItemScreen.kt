package com.example.agricult.ui.screen.home.announcementItem

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.agricult.R
import com.example.agricult.models.category.CategoryModel
import com.example.agricult.models.category.Data
import com.example.agricult.paging.ResultSource
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextPalleteGrey
import com.example.agricult.viewmodel.CategoryViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.FavouriteViewModel
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.flow.Flow


@Composable
fun AnnouncementItemScreen(
    modifier: Modifier = Modifier,
    categoryModel: List<Data>,
    dataStoreViewModel: DataStoreViewModel,
    favouriteViewModel: FavouriteViewModel,
    navHostController: NavHostController,
    categoryViewModel: CategoryViewModel,
    data: LazyPagingItems<Data>
) {


    Log.e("TAG", "AnnouncementItemScreen: ${categoryModel.size}")





    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xffE5E5E5))
            .padding(bottom = 60.dp),
    ) {


        items(data.itemCount) { item ->

            AnnouncementItems(
                data = data[item]!!,
                dataStoreViewModel = dataStoreViewModel,
                favouriteViewModel = favouriteViewModel,
                navHostController = navHostController,
                categoryViewModel = categoryViewModel
            )
        }
    }

}


@Composable
fun AnnouncementItems(
    modifier: Modifier = Modifier,
    data: Data,
    favouriteViewModel: FavouriteViewModel,
    dataStoreViewModel: DataStoreViewModel,
    navHostController: NavHostController,
    categoryViewModel: CategoryViewModel
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

    categoryViewModel.getAdsModel.value = null

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 16.dp)
            .clickable {
                categoryViewModel.getAdsById(
                    id = data.id,
                    token = getToken.value,
                    dataStoreViewModel = dataStoreViewModel
                )

                navHostController.navigate("ads_screen")
            }
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(4.dp)
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
                                fontSize = 14.sp
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
                                    Log.e("TAG", "AnnouncementItems: включить")
                                    data.is_favorite = true
                                } else {
                                    favouriteViewModel.deleteFavoriteStore(
                                        token = getToken.value,
                                        data.id
                                    )
                                    data.is_favorite = false
                                    Log.e("TAG", "AnnouncementItems: выключить")
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

