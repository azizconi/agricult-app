package com.example.agricult.ui.screen.home.announcement

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agricult.R
import com.example.agricult.ui.screen.home.categories.category.CategoryScreen
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun AnnouncementScreen(
    modifier: Modifier = Modifier
) {

    AnnouncementScreenToolbar()

}

@Composable
fun AnnouncementScreenToolbar(
    modifier: Modifier = Modifier
) {

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

            }

        },
        navigationIcon = {
            IconButton(onClick = {}) {
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
                .width(80.dp)
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {

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


            IconButton(
                onClick = {

                },
                modifier = modifier
                    .size(24.dp)
            ) {
                Icon(
                    painter = rememberCoilPainter(request = R.drawable.drop_menu),
                    contentDescription = "menu",
                    modifier = Modifier
                        .size(18.dp),
                    tint = Color.White,


                    )
            }
        }



        },
        backgroundColor = PrimaryColorGreen,
        modifier = modifier.height(56.dp)

    )

}


@Preview
@Composable
fun PreviewAnnouncementScreen() {
    AnnouncementScreen()
}