package com.example.agricult.ui.screen.home.addAnnouncement

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.agricult.models.categories.CategoriesModel
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextFieldColor
import com.example.agricult.viewmodel.CategoriesViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.ProfileRequestViewModel


@Composable
fun AddAnnouncement(
    modifier: Modifier = Modifier,
    dataStoreViewModel: DataStoreViewModel,
    navHostController: NavHostController,
    categoriesViewModel: CategoriesViewModel,
    profileRequestViewModel: ProfileRequestViewModel,
) {

    val categoriesModels = categoriesViewModel.getCategoriesModel.value


    profileRequestViewModel.getShowProfileUser(
        dataStoreViewModel.readFromDataStore.value.toString(),
        dataStoreViewModel = dataStoreViewModel
    )


    val profileRequestModel = profileRequestViewModel.getShowUserData.value

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current


    val labelTextColor by remember {
        mutableStateOf(Color(0xff444444))
    }

    var nameAnnouncement by rememberSaveable {
        mutableStateOf("")
    }

    var priceAnnouncement by rememberSaveable {
        mutableStateOf("")
    }

    var phone by rememberSaveable {
        mutableStateOf("")
    }

    var address by rememberSaveable {
        mutableStateOf("")
    }

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var nameAccess by remember {
        mutableStateOf(true)
    }

    var phoneAccess by remember {
        mutableStateOf(true)
    }

    var addressAccess by remember {
        mutableStateOf(true)
    }
    var emailAccess by remember {
        mutableStateOf(true)
    }


    var expanded by remember { mutableStateOf(false) }

    val categories = remember {
        mutableStateOf(CategoriesModel(title = ""))
    }
    var categoriesModel by remember { mutableStateOf("") }
    var getCategoriesId by remember { mutableStateOf(0) }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "???????????? ????????????????????",
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

        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            Column(
                modifier = modifier
                    .padding(top = 32.dp, bottom = 24.dp)
            ) {
                Text(
                    text = "???????????????? ????????????????????",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color(0xff333333)
                )

            }



            Column(
                modifier = modifier
                    .padding(bottom = 24.dp)
            ) {

                Text(
                    text = "????????????????",
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
                            indicatorInputs(nameAccess),
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
                        value = nameAnnouncement,
                        onValueChange = {
                            nameAnnouncement = it

                            nameAccess = nameAnnouncement.length >= 6

                            if (nameAnnouncement.isEmpty()) {
                                nameAccess = true
                            }
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


            Column(
                modifier = modifier
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "??????????????????",
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
                        value = priceAnnouncement,
                        onValueChange = {
                            priceAnnouncement = it


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
                                    text = "?????? / ??????????",
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

            Column(
                modifier = modifier
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "?????????? ????????????????",
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
                            indicatorInputs(phoneAccess),
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
                        value = phone,
                        onValueChange = {
                            if (it.length <= 9) {
                                phone = it
                            }



                            phoneAccess = phone.length == 9

                            if (phone.isEmpty()) {
                                phoneAccess = true
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


            Column(
                modifier = modifier
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "??????????",
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
                            indicatorInputs(addressAccess),
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
                        value = address,
                        onValueChange = {
                            address = it


                            addressAccess = it.length >= 6

                            if (address.isEmpty()) {
                                addressAccess = true
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


            Column(
                modifier = modifier
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "?????????????????????? ??????????",
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
                            indicatorInputs(emailAccess),
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
                        value = email,
                        onValueChange = {
                            email = it

                            val indexEmail =  mutableStateOf(0)
                            val emailDogSymbol = mutableStateOf(false)



                            it.forEachIndexed { index, symbol ->
                                emailDogSymbol.value = symbol == '@'
                                if (emailDogSymbol.value) {
                                    indexEmail.value = index
                                }
                            }



                            emailAccess = emailDogSymbol.value || (indexEmail.value + 5) <= email.length




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

            Column(
                modifier = modifier
                    .padding(bottom = 52.dp)

            ) {

                Text(
                    text = "??????????????????",
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
                        value = categoriesModel,
                        onValueChange = { categoriesModel = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
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
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ) {
                categoriesModels.forEach { label ->
                    DropdownMenuItem(onClick = {
                        categoriesModel = label.title!!
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

        Column(
            modifier = modifier
                .padding(bottom = 72.dp, top = 11.dp)
                .padding(horizontal = 16.dp)

        ) {



            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorGreen),
                onClick = {


                    if (nameAnnouncement == "" || nameAnnouncement.isEmpty() &&
                        priceAnnouncement == "" || priceAnnouncement.isEmpty() &&
                        phone == "" || phone.isEmpty() &&
                        address == "" || address.isEmpty() &&
                        email == "" || email.isEmpty() &&
                        categories.value.title == "" || categories.value.title!!.isEmpty() ||
                        !nameAccess || !phoneAccess || !addressAccess || !emailAccess
                    ) {
                        Toast.makeText(
                            context,
                            "?????????????????? ????????",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        navHostController.navigate(
                            "addAdsSecondScreen?price=$priceAnnouncement&" +
                                    "address=$address&" +
                                    "title=$nameAnnouncement&" +
                                    "phone=$phone&" +
                                    "email=$email&" +
                                    "categoryId=${categories.value.id}&" +
                                    "userId=${profileRequestModel.id}"
                        )


                    }
                }
            ) {
                Text(
                    text = "????????????????????",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }

    }
}


fun indicatorInputs(access: Boolean): Color {
    val indicatorColor = if (access) {
        Color(0xffD4D4D4)
    } else {
        Color.Red
    }

    return indicatorColor
}