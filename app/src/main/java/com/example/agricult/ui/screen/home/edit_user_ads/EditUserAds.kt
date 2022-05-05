package com.example.agricult.ui.screen.home.edit_user_ads

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.models.addAds.AddAds
import com.example.agricult.models.categories.CategoriesModel
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextFieldColor
import com.example.agricult.viewmodel.CategoriesViewModel
import com.example.agricult.viewmodel.CategoryViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun EditUserAds(
    modifier: Modifier = Modifier,
    dataStoreViewModel: DataStoreViewModel,
    navHostController: NavHostController,
    categoriesViewModel: CategoriesViewModel,
    categoryViewModel: CategoryViewModel
) {
    val categoriesModels = categoriesViewModel.getCategoriesModel.value


    if (categoryViewModel.getMyAdsModel != null) {
        val adsData = categoryViewModel.getMyAdsModel


        val getToken = remember {
            mutableStateOf("")
        }

        getToken.value = dataStoreViewModel.readFromDataStore.value.toString()




        Log.e("TAG", "EditUserAds: $adsData")
        val context = LocalContext.current

        val focusManager = LocalFocusManager.current


        val labelTextColor by remember {
            mutableStateOf(Color(0xff444444))
        }

        var nameAnnouncement by remember {
            mutableStateOf(adsData?.title)
        }

        var priceAnnouncement by remember {
            mutableStateOf(adsData?.price)
        }

        var phone by remember {
            mutableStateOf(adsData?.phone)
        }

        var address by remember {
            mutableStateOf(adsData?.address)
        }

        var email by remember {
            mutableStateOf(adsData?.email)
        }

        var description by remember {
            mutableStateOf(adsData?.description)
        }

        var expanded by remember { mutableStateOf(false) }

        val categories = remember {
            mutableStateOf(CategoriesModel(title = ""))
        }
        var categoriesModel by remember { mutableStateOf(adsData?.category) }
        var categoryTitle by remember {
            mutableStateOf(categoriesModel?.title)
        }
        var getCategoriesId by remember { mutableStateOf(adsData?.category?.id) }

        var textfieldSize by remember { mutableStateOf(Size.Zero) }

        val icon = if (expanded)
            Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown

        Column(
            modifier = modifier
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Редактированить объявление",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = Color.White
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



            LazyColumn(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {

                item {
                    Column(
                        modifier = modifier
                            .padding(top = 32.dp, bottom = 24.dp)
                    ) {
                        Text(
                            text = "Основная информация",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            color = Color(0xff333333)
                        )

                    }
                }



                item {
                    Column(
                        modifier = modifier
                            .padding(bottom = 24.dp)
                    ) {

                        Text(
                            text = "Название",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = labelTextColor,
                            modifier = modifier
                                .padding(bottom = 5.dp)
                        )


                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 64.dp)
                                .border(
                                    1.dp, /* Color(0xffD4D4D4) */
                                    Color(0xffD4D4D4),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                        ) {

                            BasicTextField(
                                modifier = modifier
                                    .background(
                                        TextFieldColor,
                                        MaterialTheme.shapes.small
                                    )
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 64.dp),
                                value = nameAnnouncement.toString(),
                                onValueChange = {
                                    nameAnnouncement = it


                                },
                                singleLine = false,
                                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color(0xff444444),
                                    fontSize = 16.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = modifier,
                                    ) {

                                        Box(
                                            Modifier
                                                .weight(1f)
                                                .padding(
                                                    start = 16.dp,
                                                    end = 16.dp,
                                                    top = 12.dp,
                                                    bottom = 14.dp
                                                )
                                        ) {

                                            innerTextField()
                                        }


                                    }
                                },
                            )
                        }
                    }
                }




                item {
                    Column(
                        modifier = modifier
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Стоимость",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = labelTextColor,
                            modifier = modifier
                                .padding(bottom = 5.dp)

                        )


                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .border(
                                    1.dp, Color(0xffD4D4D4),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                        ) {
                            BasicTextField(
                                modifier = modifier
                                    .background(
                                        TextFieldColor,
                                        MaterialTheme.shapes.small
                                    )
                                    .height(40.dp)
                                    .fillMaxWidth(),
                                value = priceAnnouncement.toString(),
                                onValueChange = {
                                    priceAnnouncement = it.toDouble()
                                },
                                singleLine = true,
                                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color(0xff444444),
                                    fontSize = 16.sp
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Number
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = modifier,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {

                                        Box(
                                            Modifier
                                                .weight(1f)
                                                .padding(start = 16.dp, top = 10.dp, bottom = 11.dp)
                                        ) {

                                            innerTextField()
                                        }

                                        Divider(
                                            color = Color(0xffD4D4D4),
                                            modifier = Modifier
                                                .height(40.dp)
                                                .width(1.dp)
                                        )

                                        Text(
                                            text = "смн / сутки",
                                            modifier = modifier.padding(
                                                start = 8.dp,
                                                top = 10.dp,
                                                end = 9.dp,
                                                bottom = 11.dp
                                            ),
                                            color = Color(0xff444444)
                                        )
                                    }
                                },

                                )
                        }


                    }
                }


                item {
                    Column(
                        modifier = modifier
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Номер телефона",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = labelTextColor,
                            modifier = modifier
                                .padding(bottom = 5.dp)
                        )

                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .border(
                                    1.dp, /* Color(0xffD4D4D4) */
                                    Color(0xffD4D4D4),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                        ) {
                            BasicTextField(
                                modifier = modifier
                                    .background(
                                        TextFieldColor,
                                        MaterialTheme.shapes.small
                                    )
                                    .height(40.dp)
                                    .fillMaxWidth(),
                                value = phone.toString(),
                                onValueChange = {
                                    if (it.length <= 9) {
                                        phone = it
                                    }


                                },
                                singleLine = true,
                                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color(0xff444444),
                                    fontSize = 16.sp
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Number
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = modifier.padding(bottom = 1.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {

                                        Text(
                                            text = "+992 ",
                                            modifier = modifier.padding(
                                                start = 8.dp,
                                                top = 10.dp,
                                                end = 9.dp,
                                                bottom = 11.dp
                                            ),
                                            color = Color(0xff444444)
                                        )

                                        Divider(
                                            color = Color(0xffD4D4D4),
                                            modifier = Modifier
                                                .height(40.dp)
                                                .width(1.dp)
                                        )

                                        Box(
                                            Modifier
                                                .weight(1f)
                                                .padding(start = 16.dp, top = 10.dp, bottom = 11.dp)
                                        ) {

                                            innerTextField()
                                        }


                                    }
                                },
                            )
                        }

                    }
                }



                item {
                    Column(
                        modifier = modifier
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Адрес",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = labelTextColor,
                            modifier = modifier
                                .padding(bottom = 5.dp)
                        )

                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .border(
                                    1.dp,/* Color(0xffD4D4D4) */
                                    Color(0xffD4D4D4),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                        ) {
                            BasicTextField(
                                modifier = modifier
                                    .background(
                                        TextFieldColor,
                                        MaterialTheme.shapes.small
                                    )
                                    .height(40.dp)
                                    .fillMaxWidth(),
                                value = address.toString(),
                                onValueChange = {
                                    address = it


                                },
                                singleLine = true,
                                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color(0xff444444),
                                    fontSize = 16.sp
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Text
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = modifier.padding(bottom = 1.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {


                                        Box(
                                            Modifier
                                                .weight(1f)
                                                .padding(start = 16.dp, top = 10.dp, bottom = 11.dp)
                                        ) {

                                            innerTextField()
                                        }


                                    }
                                },
                            )
                        }

                    }
                }

                item {
                    Column(
                        modifier = modifier
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Электронная почта",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = labelTextColor,
                            modifier = modifier
                                .padding(bottom = 5.dp)
                        )

                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .border(
                                    1.dp,/* Color(0xffD4D4D4)*/
                                    Color(0xffD4D4D4),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                        ) {
                            BasicTextField(
                                modifier = modifier
                                    .background(
                                        TextFieldColor,
                                        MaterialTheme.shapes.small
                                    )
                                    .height(40.dp)
                                    .fillMaxWidth(),
                                value = email.toString(),
                                onValueChange = {
                                    email = it

                                    val indexEmail = mutableStateOf(0)
                                    val emailDogSymbol = mutableStateOf(false)



                                    it.forEachIndexed { index, symbol ->
                                        emailDogSymbol.value = symbol == '@'
                                        if (emailDogSymbol.value) {
                                            indexEmail.value = index
                                        }
                                    }


                                },
                                singleLine = true,
                                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color(0xff444444),
                                    fontSize = 16.sp
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Email
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = modifier.padding(bottom = 1.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {


                                        Box(
                                            Modifier
                                                .weight(1f)
                                                .padding(start = 16.dp, top = 10.dp, bottom = 11.dp)
                                        ) {

                                            innerTextField()
                                        }


                                    }
                                },
                            )
                        }

                    }
                }

                item {
                    Column(
                        modifier = modifier
                            .padding(bottom = 62.dp)
                    ) {

                        Text(
                            text = "Описание",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = labelTextColor,
                            modifier = modifier
                                .padding(bottom = 5.dp)
                        )

                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 64.dp)
                                .border(1.dp, Color(0xffD4D4D4), shape = RoundedCornerShape(4.dp)),
                        ) {

                            BasicTextField(
                                modifier = modifier
                                    .background(
                                        TextFieldColor,
                                        MaterialTheme.shapes.small
                                    )
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 186.dp),
                                value = description.toString(),
                                onValueChange = {
                                    description = it
                                },
                                singleLine = false,
                                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color(0xff444444),
                                    fontSize = 14.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = modifier,
                                    ) {

                                        Box(
                                            Modifier
                                                .weight(1f)
                                                .padding(
                                                    start = 16.dp,
                                                    end = 16.dp,
                                                    top = 12.dp,
                                                    bottom = 14.dp
                                                )
                                        ) {

                                            innerTextField()
                                        }

                                    }
                                },
                            )
                        }

                    }
                }


                item {
                    Column(
                        modifier = modifier
                            .padding(bottom = 52.dp)

                    ) {

                        Text(
                            text = "Категория",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = labelTextColor,
                            modifier = modifier
                                .padding(bottom = 5.dp)

                        )

                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .border(1.dp, Color(0xffD4D4D4), shape = RoundedCornerShape(4.dp)),
                        ) {
                            BasicTextField(
                                value = categoryTitle.toString(),
                                onValueChange = { categoryTitle = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { expanded = !expanded }
                                    .onGloballyPositioned { coordinates ->
                                        textfieldSize = coordinates.size.toSize()
                                    }
                                    .height(40.dp)
                                    .background(
                                        TextFieldColor,
                                        MaterialTheme.shapes.small
                                    ),

                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = modifier.padding(bottom = 1.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {


                                        Box(
                                            Modifier
                                                .weight(1f)
                                                .padding(start = 16.dp, top = 10.dp, bottom = 11.dp)
                                        ) {

                                            innerTextField()
                                        }

                                        Icon(icon, "contentDescription",
                                            Modifier.clickable { expanded = !expanded })

                                    }
                                },

                                enabled = false,
                                textStyle = LocalTextStyle.current.copy(
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontSize = 14.sp,
                                    color = Color(0xff444444)

                                ),

                                )
                        }

                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    ) {
                        categoriesModels.forEach { label ->
                            DropdownMenuItem(onClick = {
                                categoryTitle = label.title!!
                                getCategoriesId = label.id!!
                                categories.value = label
                                expanded = false
                            }) {
                                Text(text = label.title!!)
                                Log.e("TAG", "AddAnnouncement: ${categories.value}")
                            }
                        }
                    }
                }


                item {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
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

                    ) {

                    }
                }


                item {
                    Column(
                        modifier = modifier
                            .padding(bottom = 72.dp, top = 11.dp)


                    ) {


                        Button(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorGreen),
                            onClick = {


                                val addAds = AddAds(
                                    title = nameAnnouncement,
                                    price = priceAnnouncement.toString(),
                                    description = description,
                                    address = address,
                                    phone = phone,
                                    categoryId = getCategoriesId,
                                    moderationStatusId = adsData!!.moderation_status!!.id,
                                    email = email

                                )
                                categoryViewModel.updateAds(
                                    token = getToken.value,
                                    id = adsData.id!!,
                                    addAds = addAds,
                                    dataStoreViewModel = dataStoreViewModel
                                )

                                navHostController.popBackStack()
                                Log.e("TAG", "EditUserAds: ${addAds.title}")

                            }
                        ) {
                            Text(
                                text = "Сохранить",
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

            }











        }
    }
}


