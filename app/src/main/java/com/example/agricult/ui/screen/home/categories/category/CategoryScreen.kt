package com.example.agricult.ui.screen.home.categories.category

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.ui.screen.home.announcementItem.AnnouncementItemScreen
import com.example.agricult.ui.screen.home.categories.CategoriesToolbar
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.CategoryViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.FavouriteViewModel
import com.example.agricult.viewmodel.SearchViewModel
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun CategoryScreen(
    idCategory: String,
    categoryViewModel: CategoryViewModel,
    dataStoreViewModel: DataStoreViewModel,
    favouriteViewModel: FavouriteViewModel,
    searchViewModel: SearchViewModel,
    navHostController: NavHostController
) {

    var expandedAnimation by remember {
        mutableStateOf(false)
    }

    var orderByData by remember {
        mutableStateOf("desc")
    }

    var minPriceByData by remember {
        mutableStateOf("")
    }

    var maxPriceByData by remember {
        mutableStateOf("")
    }

    var onClickFilterButtonData by remember {
        mutableStateOf(false)
    }

    var searchText by remember {
        mutableStateOf("")
    }


    val getToken = remember {
        mutableStateOf("")
    }


    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()

    Column {
        CategoriesToolbar(
            param = {
                searchText = it
            },
            searchViewModel = searchViewModel,
            getToken = getToken.value,
            navHostController = navHostController
        )
        UnderSearchButtons { click, orderBy, clickSortingButton ->
            expandedAnimation = click
            orderByData = orderBy

            if (clickSortingButton) {
                categoryViewModel.getCategoryRequest(
                    categoryId = idCategory.toInt(),
                    token = dataStoreViewModel.readFromDataStore.value.toString(),
                    orderBy = "desc",
                    priceFrom = minPriceByData.toInt(),
                    priceTo = maxPriceByData.toInt(),
                )
            } else {
                categoryViewModel.getCategoryRequest(
                    categoryId = idCategory.toInt(),
                    token = dataStoreViewModel.readFromDataStore.value.toString(),
                    orderBy = "asc",
                    priceFrom = minPriceByData.toInt(),
                    priceTo = maxPriceByData.toInt(),
                )
            }


        }
        FilterScreen(
            onClickFilter = expandedAnimation,
            navHostController = navHostController,
        ) { minPrice, maxPrice, onClickSearchFilterButton ->
            minPriceByData = minPrice
            maxPriceByData = maxPrice

            onClickFilterButtonData = onClickSearchFilterButton

            Log.e("TAG", "CategoryScreen:102 $onClickFilterButtonData")
            if (onClickFilterButtonData) {
                categoryViewModel.getCategoryRequest(
                    token = dataStoreViewModel.readFromDataStore.value.toString(),
                    categoryId = idCategory.toInt(),
                    priceFrom = minPriceByData.toInt(),
                    priceTo = maxPriceByData.toInt(),
                    orderBy = orderByData
                )
            }

        }
        AnnouncementItemScreen(
            categoryModel = categoryViewModel.getCategoryModel.value,
            dataStoreViewModel = dataStoreViewModel,
            favouriteViewModel = favouriteViewModel
        )
    }


}


@Composable
fun UnderSearchButtons(
    modifier: Modifier = Modifier,
    onClick: (clickFilter: Boolean, orderBy: String, expandedSortButton: Boolean) -> Unit,
) {

    var orderByCategoryData by remember {
        mutableStateOf("desc")
    }

    var expandedFilterButton by remember {
        mutableStateOf(false)
    }

    var colorFilterButton by remember {
        mutableStateOf(Color(0xff999999))
    }

    var expandedSortButton by remember {
        mutableStateOf(true)
    }

    var iconSortButton by remember {
        mutableStateOf(R.drawable.ic_sort_v1)
    }



    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {


        Row(
            modifier = modifier
                .padding(start = 16.dp)
                .width(187.dp)
                .height(56.dp)
                .clickable {
                    expandedSortButton = !expandedSortButton





                    iconSortButton = if (expandedSortButton) {
                        R.drawable.ic_sort_v1
                    } else {
                        R.drawable.ic_sort_v2
                    }

                    onClick(expandedFilterButton, orderByCategoryData, expandedSortButton)


                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = rememberCoilPainter(
                    request = iconSortButton
                ),
                contentDescription = "Sort by",
                modifier = modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(4.dp),
            )

            Text(
                text = "По дате",
                color = Color(0xff999999),
                modifier = modifier
                    .padding(start = 8.dp),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular))
            )
        }

        Row(
            modifier = modifier
                .padding(end = 16.dp)
                .width(188.dp)
                .height(56.dp)
                .clickable {
                    expandedFilterButton = !expandedFilterButton

                    orderByCategoryData = if (expandedSortButton) {
                        "desc"
                    } else {
                        "asc"
                    }

                    onClick(expandedFilterButton, orderByCategoryData, expandedSortButton)



                    colorFilterButton = if (expandedFilterButton) {
                        PrimaryColorGreen
                    } else {
                        Color(0xff999999)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Icon(
                painter = rememberCoilPainter(request = R.drawable.filters),
                contentDescription = "Filter",
                modifier = modifier
                    .width(24.dp)
                    .height(24.dp),
                tint = colorFilterButton
            )

            Text(
                text = "Фильтры",
                color = colorFilterButton,
                modifier = modifier
                    .padding(horizontal = 6.dp),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular))
            )

        }

    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    onClickFilter: Boolean,
    navHostController: NavHostController,

    params: (minPrice: String, maxPrice: String, onClickFilterButton: Boolean) -> Unit
) {

    var minPrice by rememberSaveable {
        mutableStateOf("0")
    }

    var maxPrice by rememberSaveable {
        mutableStateOf("1000000")
    }


    var onClickButtonSearchFilter by remember {
        mutableStateOf(false)
    }

    params(minPrice, maxPrice, onClickButtonSearchFilter)

    AnimatedContent(
        targetState = onClickFilter,
        transitionSpec = {
            fadeIn(animationSpec = tween(150, 150)) with
                    fadeOut(animationSpec = tween(150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                // Expand horizontally first.
                                IntSize(targetSize.width, initialSize.height) at 150
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                // Shrink vertically first.
                                IntSize(initialSize.width, targetSize.height) at 150
                                durationMillis = 300
                            }
                        }
                    }
        }

    ) { targetExpanded ->

        if (targetExpanded) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .height(158.dp)
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = minPrice,
                        onValueChange = { minPrice = it },
                        label = { Text("От") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = modifier
                            .width(160.dp)
                            .height(60.dp),
                        colors = TextFieldDefaults
                            .outlinedTextFieldColors(
                                cursorColor = PrimaryColorGreen,
                                focusedBorderColor = PrimaryColorGreen,
                                focusedLabelColor = PrimaryColorGreen
                            )
                    )


                    Text(text = "-")


                    OutlinedTextField(
                        value = maxPrice,
                        onValueChange = { maxPrice = it },
                        label = { Text("До") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = modifier
                            .width(160.dp)
                            .height(60.dp),
                        colors = TextFieldDefaults
                            .outlinedTextFieldColors(
                                cursorColor = PrimaryColorGreen,
                                focusedBorderColor = PrimaryColorGreen,
                                focusedLabelColor = PrimaryColorGreen
                            )
                    )
                }

                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorGreen),
                    onClick = {


                        onClickButtonSearchFilter = !onClickButtonSearchFilter


//                        navHostController.navigate("search_screen?query=$searchText")

                        params(minPrice, maxPrice, onClickButtonSearchFilter)

                        onClickButtonSearchFilter = false

                    }
                ) {
                    Text(
                        text = "Поиск",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                }
            }
        }
    }
}