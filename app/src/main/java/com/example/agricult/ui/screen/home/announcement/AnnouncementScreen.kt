package com.example.agricult.ui.screen.home.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agricult.ui.screen.home.categories.category.CategoryScreen

@Composable
fun AnnouncementScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xffE5E5E5)),

        ) {
        items(5) {

            AnnouncementItems()
        }
    }
}


@Composable
fun AnnouncementItems(
    modifier: Modifier = Modifier
) {

    Row(



        verticalAlignment = Alignment.CenterVertically

    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(4.dp),
            backgroundColor = Color.White
        ) {

        }

        Column(
            modifier = modifier
                .padding(12.dp)
                .background(Color.Yellow)
                .height(124.dp)
                .width(343.dp)
        ) {

        }

    }

}


@Preview
@Composable
fun PreviewAnnouncementScreen() {
    AnnouncementScreen()
}