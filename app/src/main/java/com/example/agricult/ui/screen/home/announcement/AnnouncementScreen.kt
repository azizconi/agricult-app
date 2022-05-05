package com.example.agricult.ui.screen.home.announcement

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.models.ads.Advertisement
import com.example.agricult.ui.screen.home.contact.ContactsAddressItem
import com.example.agricult.ui.screen.home.contact.ContactsEmailItem
import com.example.agricult.ui.screen.home.contact.ContactsPhoneItem
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.CategoryViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.text.SimpleDateFormat
import kotlin.math.min


@Composable
fun AnnouncementScreen(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel,
    dataStoreViewModel: DataStoreViewModel,
    navHostController: NavHostController
) {

    val getToken = remember {
        mutableStateOf("")
    }

    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()


    val getAdsModel = categoryViewModel.getAdsModel.value


    Column(
        modifier = modifier.padding(bottom = 60.dp)
    ) {
        if (getAdsModel != null) {
            AnnouncementScreenToolbar(
                navHostController = navHostController,
                advertisement = getAdsModel
            )
        }

        Column(
            modifier = modifier
//                .padding(horizontal = 16.dp)
        ) {
            if (getAdsModel != null) {
                AdsInfo(advertisement = getAdsModel)
            }
        }

        Log.e("TAG", "AnnouncementScreen: $getAdsModel")

    }

}

@Composable
fun AnnouncementScreenToolbar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    advertisement: Advertisement
) {

    val context = LocalContext.current

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

            }

        },
        navigationIcon = {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    painter = rememberCoilPainter(request = R.drawable.back),
                    contentDescription = "back",
                    modifier = modifier
                        .width(16.dp)
                        .height(15.dp)
                        .size(20.dp),
                    tint = Color.White

                )
            }
        },
        actions = {

            Row(
                modifier = modifier

                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        shareAds(context = context, advertisement = advertisement)
                    },
                    modifier = modifier
                        .size(24.dp),

                    ) {
                    Icon(
                        painter = rememberCoilPainter(request = R.drawable.share),
                        contentDescription = "share",
                        modifier = Modifier
                            .width(19.dp)
                            .height(20.dp),
                        tint = Color.White,


                        )
                }

            }


        },
        backgroundColor = PrimaryColorGreen,
        modifier = modifier.height(56.dp)

    )

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdsInfo(
    modifier: Modifier = Modifier,
    advertisement: Advertisement
) {

    val scaffoldState = rememberScaffoldState()

    val pagerState = rememberPagerState(
        pageCount = advertisement.images!!.size,
        initialOffscreenLimit = 2,
        initialPage = 0,
        infiniteLoop = false
    )

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        scaffoldState = scaffoldState
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        HorizontalPager(
                            state = pagerState
                        ) { page ->
                            Column(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberCoilPainter(request = "http://api.agricult.colibri.tj/public/storage/" + advertisement.images[page]),
                                    contentDescription = "OnBoardImage",
                                    modifier = Modifier
                                        .width(343.dp)
                                        .height(257.dp)
                                )
                            }
                        }

                        PagerIndicator(advertisement.images.size, pagerState.currentPage)
                    }



                    item {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()

                                .drawBehind {
                                    val strokeWidth = 0.5f * density
                                    val y = size.height - strokeWidth / 2

                                    drawLine(
                                        Color(0xFFAAA9A9),
//                                    Color.LightGray,
                                        Offset(0f, y),
                                        Offset(size.width, y),
                                        strokeWidth
                                    )
                                }
                        ) {

                            AdsInfoTime(advertisement = advertisement)
                            Spacer(modifier = modifier.height(24.dp))
                        }
                    }

                    item {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    val strokeWidth = 0.5f * density
                                    val y = size.height - strokeWidth / 2

                                    drawLine(
                                        Color(0xFFAAA9A9),
                                        Offset(0f, y),
                                        Offset(size.width, y),
                                        strokeWidth
                                    )
                                }

                        ) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .drawBehind {
                                        val strokeWidth = 0.5f * density
                                        val y = size.height - strokeWidth / 2

                                        drawLine(
                                            Color(0xFFAAA9A9),
                                            Offset(0f, y),
                                            Offset(size.width, y),
                                            strokeWidth
                                        )
                                    }

                            ) {
                                AdsPrice(advertisement = advertisement)
                            }
                        }

                    }

                    item {
                        AdsDescription(advertisement = advertisement)
                    }

                    item {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    val strokeWidth = 0.5f * density
                                    val y = size.height - strokeWidth / 2

                                    drawLine(
                                        Color(0xFFAAA9A9),
                                        Offset(0f, y),
                                        Offset(size.width, y),
                                        strokeWidth
                                    )
                                }
                        ) {

                        }
                    }


                    if (advertisement.phone != null) {
                        item {
                            ContactsPhoneItem(phone = advertisement.phone.toString())
                        }
                    }

                    if (advertisement.email != null) {
                        item {
                            ContactsEmailItem(email = advertisement.email.toString())
                        }
                    }

                    if (advertisement.address != null) {
                        item {
                            ContactsAddressItem(address = advertisement.address.toString())
                        }
                    }


                }


            }
        }
    }

}


@Composable
fun PagerIndicator(size: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 4.dp)
    ) {
        repeat(size) {
            IndicateIcon(
                isSelected = it == currentPage
            )
        }
    }
}

@Composable
fun IndicateIcon(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp
    )

    Box(
        modifier = Modifier
            .padding(2.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) {
                    Color.Green
                } else {
                    Color.Gray.copy(alpha = 0.5f)
                }
            )
    )
}


@Composable
fun AdsInfoTime(
    modifier: Modifier = Modifier,
    advertisement: Advertisement
) {


    Column(
        modifier = modifier
            .padding(top = 8.dp)

            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${getData(advertisement.created_at.toString())}     №${advertisement.id}",
            fontSize = 12.sp,
            color = Color(0xFF8B8B8B),
            fontFamily = FontFamily(Font(R.font.roboto_regular))
        )

        Spacer(modifier = modifier.height(12.dp))

        Text(
            text = advertisement.title.toString(),
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.roboto_medium)),

            )
    }

}

@Composable
fun AdsPrice(
    modifier: Modifier = Modifier,
    advertisement: Advertisement
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Стоимость:",
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            color = Color(0xff999999)
        )

        Text(
            text = "${advertisement.price} смн / сутки",
            color = PrimaryColorGreen,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.roboto_medium))
        )
    }
}

@Composable
fun AdsDescription(
    modifier: Modifier = Modifier,
    advertisement: Advertisement
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 78.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Описание:",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                color = Color(0xff444444),
                fontWeight = FontWeight(500)
            )
        }


        Text(
            text = advertisement.description.toString(),
            color = Color(0xff444444),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular))
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun getData(dateAds: String): String {
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000000'Z'")


    val monthName = listOf(
        "Январь",
        "Февраль",
        "Март",
        "Апрель",
        "Май",
        "Июнь",
        "Июль",
        "Август",
        "Сентябрь",
        "Октябрь",
        "Ноябрь",
        "Декабрь"
    )


    val formatDay = "dd"
    val formatMonth = "MM"
    val formatYear = "yyyy"

    val formatTime = "HH:mm"


    Log.e("TAG", "getData: $dateAds")
    val getFormatDay = SimpleDateFormat(formatDay).format(date.parse(dateAds)!!)
    val getFormatMonth = SimpleDateFormat(formatMonth).format(date.parse(dateAds)!!)
    val getFormatYear = SimpleDateFormat(formatYear).format(date.parse(dateAds)!!)
    val getFormatTime = SimpleDateFormat(formatTime).format(date.parse(dateAds)!!)


    return "$getFormatDay ${monthName[getFormatMonth.toInt() - 1]} $getFormatYear г.     $getFormatTime"
}


fun shareAds(advertisement: Advertisement, context: Context) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(
        Intent.EXTRA_TEXT, "${advertisement.title}" +
                "\n" +
                "Цена: ${advertisement.price} сом/сутки" + "\n" + "\n" +
                "https://agricult.tj/advertisement/${advertisement.id}"
    )
    intent.type = "text/plain"
    context.startActivity(Intent.createChooser(intent, "Отправить в: "))
}