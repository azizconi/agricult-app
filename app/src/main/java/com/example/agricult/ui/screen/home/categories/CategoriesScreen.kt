package com.example.agricult.ui.screen.home.categories

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.models.categories.CategoriesModel
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.*
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun Categories(
    categoriesViewModel: CategoriesViewModel,
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel,
    navHostController: NavHostController,
    searchViewModel: SearchViewModel,
    dataStoreViewModel: DataStoreViewModel,
    params: (query: String) -> Unit
) {

    val getToken = remember {
        mutableStateOf("")
    }


    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()



    categoriesViewModel.getCategoriesRequest(
        getToken.value,
        dataStoreViewModel = dataStoreViewModel
    )
    val getCategoriesModel = categoriesViewModel.getCategoriesModel.value


    Column {
        CategoriesToolbar(
            param = {
                params(it)
            },
            searchViewModel = searchViewModel,
            getToken = getToken.value,
            navHostController = navHostController,
            dataStoreViewModel = dataStoreViewModel
        )

        Column(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)

        ) {
            LazyColumn {
                items(getCategoriesModel.size) {
                    CategoriesItem(
                        categoriesModel = getCategoriesModel[it],
                        categoryViewModel = categoryViewModel,
                        getToken = getToken.value,
                        navHostController = navHostController,
                        dataStoreViewModel = dataStoreViewModel
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CategoriesToolbar(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    getToken: String,
    navHostController: NavHostController,
    dataStoreViewModel: DataStoreViewModel,
    param: (query: String) -> Unit
) {


    TopAppBar(
        title = {
            SearchBar(
                onSearchClicked = {
                    param(it)
                }, searchViewModel = searchViewModel,
                getToken = getToken,
                navHostController = navHostController,
                dataStoreViewModel = dataStoreViewModel
            ) { onClick ->


            }
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
    navHostController: NavHostController,
    dataStoreViewModel: DataStoreViewModel,
    onSearchClicked: (String) -> Unit,
    params: (onClick: Boolean) -> Unit
) {

    var text by remember {
        mutableStateOf("")
    }

    var onClickSearchButton by remember {
        mutableStateOf(false)
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
                    if (text.isNotEmpty() || text != "") {
                        onClickSearchButton = true

                        searchViewModel.getSearchAnnouncement(
                            query = text,
                            token = getToken,
                            priceFrom = 0,
                            priceTo = 1000000,
                            orderBy = "desc",
                            page = 1,
                            dataStoreViewModel = dataStoreViewModel
                        )

                        navHostController.navigate("search_screen?query=$text")

                        params(onClickSearchButton)

                        keyboardController?.hide()

                        onClickSearchButton = false
                        keyboardController?.hide()
                    }

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
                            "??????????",
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
                                onClickSearchButton = true

                                searchViewModel.getSearchAnnouncement(
                                    query = text,
                                    token = getToken,
                                    priceFrom = 0,
                                    priceTo = 1000000,
                                    orderBy = "desc",
                                    page = 1,
                                    dataStoreViewModel = dataStoreViewModel
                                )

                                navHostController.navigate("search_screen?query=$text")

                                params(onClickSearchButton)

                                keyboardController?.hide()

                                onClickSearchButton = false
                            }


                        }) {
                        Icon(
                            painter = rememberCoilPainter(request = R.drawable.search),
                            contentDescription = "SearchBarIcon",
                            tint = Color(0xff999999)
                        )
                    }

                }
            },
        )
    }
}


@Composable
fun CategoriesItem(
    modifier: Modifier = Modifier,
    categoriesModel: CategoriesModel,
    categoryViewModel: CategoryViewModel,
    getToken: String,
    navHostController: NavHostController,
    dataStoreViewModel: DataStoreViewModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
//            .height(70.dp)
            .drawBehind {
                val strokeWidth = 0.5f * density
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }
            .clickable {

                categoryViewModel.getCategoryRequest(
                    token = getToken,
                    categoryId = categoriesModel.id!!,
                    priceFrom = "0",
                    priceTo = "1000000",
                    orderBy = "desc",
                    page = 1,
                    dataStoreViewModel = dataStoreViewModel
                )


                navHostController.navigate("category_screen?id=${categoriesModel.id}")


            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberCoilPainter(request = "http://api.agricult.colibri.tj/public/storage/${categoriesModel.icon}"),
            contentDescription = categoriesModel.title,
            modifier = modifier
                .width(44.dp)
                .height(44.dp)
        )

        Column(
            modifier = modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = categoriesModel.title.toString(),
                fontSize = 18.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.roboto_medium
                    )
                ),
                modifier = modifier.padding(top = 8.dp, bottom = 4.5.dp)
            )

            Text(
                text = "${categoriesModel.quantity} ??????????????????", fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.roboto_regular
                    )
                ),
                modifier = modifier
                    .padding(top = 4.5.dp, bottom = 8.dp)
            )
        }

    }
}