package com.example.agricult.ui.screen.home.announcementItem

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agricult.R
import com.example.agricult.models.category.Data
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextPalleteGrey
import com.example.agricult.viewmodel.CategoryViewModel
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun AnnouncementItemScreen(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel
) {
    val getCategoryData = categoryViewModel.getCategoryModel.value

//    val context = LocalContext.current
//    Toast.makeText(context, categoryViewModel.getCategoryModel.value.size)
    Log.e("TAG", "AnnouncementItemScreen: ${getCategoryData.size}")


    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xffE5E5E5))
            .padding(bottom = 60.dp),

        ) {
        items(getCategoryData.size) {

            AnnouncementItems(data = getCategoryData[it])
        }
    }
}


@Composable
fun AnnouncementItems(
    modifier: Modifier = Modifier,
    data: Data
) {

    val isFavouriteColor = remember {
        mutableStateOf(Color.Red)
    }


    if (data.is_favorite) {
        isFavouriteColor.value = Color.Red
    } else {
        isFavouriteColor.value = Color.White
    }

    Row(


        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 16.dp)

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
                                fontSize = 14.sp

                            )

                        }

                    }

                    Column(
                        modifier = modifier
                            .padding(start = 14.dp, top = 5.dp)
                    ) {
                        IconButton(
                            onClick = {},
                            modifier = modifier
                                .width(22.dp)
                                .height(20.dp)

                        ) {
                            Icon(
                                painter = rememberCoilPainter(request = R.drawable.heart),
                                contentDescription = "favorite",
                                modifier = modifier
                                    .width(20.dp)
                                    .height(18.dp),
                                
                                tint = if (!data.is_favorite) {
                                    TextPalleteGrey
                                } else {
                                    Color.Red
                                },


                            )
                        }

                    }

                }
            }


        }


    }

}


//@Preview
//@Composable
//fun PreviewAnnouncementScreen() {
//    AnnouncementItemScreen()
//}