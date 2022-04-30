package com.example.agricult.ui.screen.home.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agricult.ui.screen.home.categories.category.FilterScreen
import com.example.agricult.ui.screen.home.categories.category.UnderSearchButtons
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.FavouriteViewModel
import com.example.agricult.viewmodel.SearchViewModel
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun SearchScreen(
    dataStoreViewModel: DataStoreViewModel,
    searchViewModel: SearchViewModel,
    favouriteViewModel: FavouriteViewModel,
    navHostController: NavHostController,
    query: String
) {

    val getToken = remember {
        mutableStateOf("")
    }

    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()



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

    searchText = query


    Column {
        SearchToolbar(
            searchViewModel = searchViewModel,
            getToken = getToken.value,
        ) {

        }
        UnderSearchButtons { click, orderBy, clickSortingButton ->
            expandedAnimation = click
            orderByData = orderBy

            if (clickSortingButton) {
                searchViewModel.getSearchAnnouncement(
                    query = searchText,
                    token = getToken.value,
                    orderBy = "desc",
                    priceFrom = minPriceByData.toInt(),
                    priceTo = maxPriceByData.toInt(),
                    page = 1
                )
            } else {
                searchViewModel.getSearchAnnouncement(
                    query = searchText,
                    token = getToken.value,
                    orderBy = "asc",
                    priceFrom = minPriceByData.toInt(),
                    priceTo = maxPriceByData.toInt(),
                    page = 1
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

            if (onClickFilterButtonData) {
                searchViewModel.getSearchAnnouncement(
                    query = searchText,
                    token = dataStoreViewModel.readFromDataStore.value.toString(),
                    priceFrom = minPriceByData.toInt(),
                    priceTo = maxPriceByData.toInt(),
                    orderBy = orderByData,
                    page = 1
                )
            }

        }

        SearchAnnouncementScreen(
            searchModel = searchViewModel.getSearchModel.value,
            favouriteViewModel = favouriteViewModel,
            dataStoreViewModel = dataStoreViewModel
        )


    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchToolbar(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    getToken: String,
    param: (query: String) -> Unit
) {


    TopAppBar(
        title = {
            SearchBar(
                onSearchClicked = {
                    param(it)
                }, searchViewModel = searchViewModel,
                getToken = getToken,
            )
        },
        backgroundColor = PrimaryColorGreen,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()

    )


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    getToken: String,
    onSearchClicked: (String) -> Unit,
) {

    var text by remember {
        mutableStateOf("")
    }


    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp),

        ) {
        BasicTextField(
            modifier = modifier
                .background(
                    Color.White,
                    MaterialTheme.shapes.small,
                )
                .height(40.dp)
                .width(343.dp),
            value = text,
            onValueChange = {
                text = it
            },
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 14.sp,

                ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    keyboardController?.hide()
                },

                ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = modifier.padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .padding(start = 13.dp, end = 13.dp)
                    ) {
                        if (text.isEmpty()) Text(
                            "Поиск",
                            style = LocalTextStyle.current.copy(
                                color = Color(0xff999999),
                                fontSize = 14.sp
                            )
                        )
                        innerTextField()
                    }

                    IconButton(
                        modifier = modifier
                            .width(24.dp)
                            .height(24.dp),
                        onClick = {

                            if (text.isNotEmpty() || text != "") {


                                searchViewModel.getSearchAnnouncement(
                                    query = text,
                                    token = getToken,
                                    priceFrom = 0,
                                    priceTo = 1000000,
                                    orderBy = "desc",
                                    page = 1
                                )


                                keyboardController?.hide()

                            }


                        }) {
                        Icon(
                            painter = rememberCoilPainter(request = com.example.agricult.R.drawable.search),
                            contentDescription = "SearchBarIcon",
                            tint = Color(0xff999999)
                        )
                    }

                }
            },
        )
    }
}