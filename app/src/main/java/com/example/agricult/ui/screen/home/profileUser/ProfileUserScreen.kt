package com.example.agricult.ui.screen.home.profileUser

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.agricult.R
import com.example.agricult.ui.screen.ErrorInternetConnection
import com.example.agricult.ui.screen.navigation.Screen
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.ProfileRequestViewModel
import com.example.agricult.viewmodel.RequestViewModel
import com.example.agricult.viewmodel.RoomViewModel
import com.google.accompanist.coil.rememberCoilPainter
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ProfileUserScreen(
    modifier: Modifier = Modifier,
    profileRequestViewModel: ProfileRequestViewModel,
    roomViewModel: RoomViewModel,
    requestViewModel: RequestViewModel,
    navController: NavController
) {


    val getTokenList = roomViewModel.getToken().observeAsState()
//    getTokenList.value?.forEach {

        getTokenList.value?.get(getTokenList.value?.size?.minus(1)!!)?.accessToken?.let { it1 ->
            profileRequestViewModel.getShowProfileUser(
                token = it1,
                roomViewModel = roomViewModel
            )
        }

//    }

    val isLock = remember {
        mutableStateOf(true)
    }

    if (isLock.value) {
        getTokenList.value?.get(getTokenList.value!!.size- 1)?.accessToken?.let { it ->
            profileRequestViewModel.getShowProfileUser(
                token = it,
                roomViewModel = roomViewModel
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
            "Личная информация",
            Color(0xFFF2C94C),
            20,
            20
        ),
        ItemMenuProfileUser(
            R.drawable.my_announcements,
            "Мои объявления",
            Color(0xFF27AE60),
            18,
            16
        ),
        ItemMenuProfileUser(R.drawable.contact_centre, "Контакт центр", Color(0xFF2D9CDB), 18, 16),
        ItemMenuProfileUser(R.drawable.log_out, "Выход", Color(0xFFEB5757), 17, 18),
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
                                painter = rememberCoilPainter(request = "http://api.agricult.colibri.tj/public/storage/" + getShowUserData.value.image),
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
                                    text = "${year - (getDate(getShowUserData.value.date_of_birth.toString())).toInt()} лет",
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
                            roomViewModel = roomViewModel,
                            requestViewModel = requestViewModel,
                            navController = navController
                        )

                    }
                }
            }


        } else {

            ErrorInternetConnection(
                roomViewModel = roomViewModel,
                profileRequestViewModel = profileRequestViewModel,
                isLock = isLock.value,

            ) {
                 isLock.value = !isLock.value
//                Log.e("TAG", "ProfileUserScreen: $it")
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
                    text = "Личный кабинет",
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = rememberCoilPainter(request = R.drawable.info),
                    contentDescription = "menu",
                    modifier = modifier
                        .size(20.dp),
                    tint = Color.White

                )
            }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(
                    painter = rememberCoilPainter(request = R.drawable.drop_menu),
                    contentDescription = "menu",
                    modifier = Modifier
                        .size(18.dp),
                    tint = Color.White


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
    roomViewModel: RoomViewModel,
    requestViewModel: RequestViewModel,
    navController: NavController
) {
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "AlertDialog", color = Color.Black) },
            text = {
                Text(
                    text = "Hello Friends! This is Our Alert Dialog...",
                    color = Color.Black
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        openDialog.value = false
                        roomViewModel.deleteToken()
                        requestViewModel.isSuccessLoading.value = false
                        navController.navigate(Screen.MainScreen.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }


                    }) {
                    Text(text = "Confirm", color = Color.Black)
                }

            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        Toast.makeText(context, "Dismiss Button Click", Toast.LENGTH_SHORT).show()
                    }) {
                    Text(text = "Dismiss", color = Color.Black)
                }
            },
            backgroundColor = Color.Green,
            contentColor = Color.White
        )
    }



    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                openDialog.value = true
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


@SuppressLint("SimpleDateFormat")
fun getDate(age: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    val format = "yyyy"

    val simpleDateFormat = SimpleDateFormat(format)


    return simpleDateFormat.format(dateFormat.parse(age) as Date)
}