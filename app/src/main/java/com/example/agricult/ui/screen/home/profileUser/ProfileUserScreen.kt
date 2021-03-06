package com.example.agricult.ui.screen.home.profileUser

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.ui.screen.ErrorInternetConnection
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.ProfileRequestViewModel
import com.example.agricult.viewmodel.LoginViewModel
import com.google.accompanist.coil.rememberCoilPainter
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ProfileUserScreen(
    modifier: Modifier = Modifier,
    profileRequestViewModel: ProfileRequestViewModel,
    loginViewModel: LoginViewModel,
    navHostController: NavHostController,
    dataStoreViewModel: DataStoreViewModel
) {


    val isLock = remember {
        mutableStateOf(true)
    }

    val getToken = dataStoreViewModel.readFromDataStore.value

    if (isLock.value) {
        if (getToken != null) {
            profileRequestViewModel.getShowProfileUser(
                token = getToken,
                dataStoreViewModel = dataStoreViewModel
            )
        }

        isLock.value = false
    }

    val getShowUserData = profileRequestViewModel.getShowUserData
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]


    val itemMenuProfileUser = listOf(
        ItemMenuProfileUser(
            R.drawable.user_information,
            "???????????? ????????????????????",
            Color(0xFFF2C94C),
            20,
            20,
            "user_info_screen"
        ),
        ItemMenuProfileUser(
            R.drawable.my_announcements,
            "?????? ????????????????????",
            Color(0xFF27AE60),
            18,
            16,
            "user_ads_screen"
        ),
        ItemMenuProfileUser(
            R.drawable.contact_centre,
            "?????????????? ??????????",
            Color(0xFF2D9CDB),
            18,
            16,
            "contact_screen"
        ),
        ItemMenuProfileUser(R.drawable.log_out, "??????????", Color(0xFFEB5757), 17, 18, "log_out"),
    )


    Column {
        ToolbarProfileUserScreen()



        if (profileRequestViewModel.isSuccessLoadingProfile.value) {


            LazyColumn(
                modifier = modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {

                item {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(119.dp)
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

                        Row(
                            modifier = modifier
                                .padding(16.dp)
                        ) {
                            Image(
                                painter = rememberCoilPainter(
                                    request = "http://api.agricult.colibri.tj/public/storage/" + getShowUserData.value.image,
                                    requestBuilder = {

                                            placeholder(R.drawable.ic_baseline_image_24)

                                    }
                                ),
                                contentDescription = "photo profile",
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .width(87.dp)
                                    .height(87.dp)
                                    .clip(shape = RoundedCornerShape(600.dp))

                            )


                            Column(
                                modifier = modifier
                                    .fillMaxWidth()

                                    .height(87.dp)
                                    .padding(start = 16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "${getShowUserData.value.surname} ${getShowUserData.value.name}",
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                    color = Color(0xFF444444),
                                )

                                Text(
                                    text = getShowUserData.value.date_of_birth.toString(),
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    color = Color(0xFF999999),

                                    )
                                Text(
                                    text = getShowUserData.value.address.toString(),
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    color = Color(0xFF999999),
                                )

                            }
                        }

                    }
                }

                items(itemMenuProfileUser.size) {
                    Column(modifier = modifier
                        .fillMaxWidth()
                        .height(60.dp)
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
                        MenuItems(
                            itemMenuProfileUser = itemMenuProfileUser[it],
                            dataStoreViewModel = dataStoreViewModel,
                            navHostController = navHostController
                        )

                    }
                }
            }


        } else {

            ErrorInternetConnection(
                isLock = isLock.value,

                ) {
                isLock.value = !isLock.value
            }
        }
    }


}


@Composable
fun ToolbarProfileUserScreen(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "???????????? ??????????????",
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

        },

        backgroundColor = PrimaryColorGreen,
        modifier = modifier.height(56.dp)
    )
}


@Composable
fun MenuItems(
    modifier: Modifier = Modifier,
    itemMenuProfileUser: ItemMenuProfileUser,
    dataStoreViewModel: DataStoreViewModel,
    navHostController: NavHostController
) {
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "???? ?????????????????????????? ???????????? ???????????", color = Color.Black) },


            confirmButton = {

                TextButton(
                    onClick = {
                        openDialog.value = false

                        dataStoreViewModel.clearDataStore()
                    }) {
                    Text(text = "????", color = PrimaryColorGreen)
                }

            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text(text = "??????", color = PrimaryColorGreen)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.White
        )
    }



    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                if (itemMenuProfileUser.title == "??????????") {
                    openDialog.value = true
                } else {
                    navHostController.navigate(itemMenuProfileUser.route)
                }
            }
    ) {

        Card(
            shape = RoundedCornerShape(4.dp),
            backgroundColor = itemMenuProfileUser.color,
            modifier = modifier
                .size(28.dp)
        ) {
            Icon(
                painter = rememberCoilPainter(request = itemMenuProfileUser.icon),
                contentDescription = itemMenuProfileUser.title,
                modifier = modifier
                    .width(itemMenuProfileUser.width.dp)
                    .height(itemMenuProfileUser.height.dp)
                    .padding(5.dp)

            )
        }


        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .height(60.dp)
        ) {
            Text(
                text = itemMenuProfileUser.title,
                modifier = modifier.padding(start = 18.dp),
                textAlign = TextAlign.Center
            )
        }


    }
}


