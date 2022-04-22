package com.example.agricult.ui.screen.home.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agricult.R
import com.example.agricult.ui.screen.home.announcementItem.AnnouncementItemScreen
import com.example.agricult.ui.screen.home.categories.SearchBar
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.DataStoreViewModel


@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    dataStoreViewModel: DataStoreViewModel,

    ) {

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Избранное",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color.White
                )
            },
            backgroundColor = PrimaryColorGreen,
            modifier = modifier
                .height(56.dp)
                .fillMaxWidth(),


        )



//        AnnouncementItemScreen(categoryModel = )


    }

}
