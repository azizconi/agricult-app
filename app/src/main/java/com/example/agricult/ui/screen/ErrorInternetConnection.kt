package com.example.agricult.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agricult.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorInternetConnection(
    modifier: Modifier = Modifier,

    isLock: Boolean,
    onClick: (Boolean) -> Unit,
) {





    Column {
        Spacer(
            modifier = modifier
                .height(150.dp)
                .clickable {
                    onClick(isLock)
                }
        )

        Card(
            modifier = modifier
                .padding(12.dp)
                .height(120.dp)
                .fillMaxWidth(),
            backgroundColor = Color(0xFFf4f4f4),
            shape = RoundedCornerShape(10.dp),
        ) {

            Column {

                Text(
                    text = "Не удалось подключиться к серверу. Проверте подключение к интернету.",
                    fontSize = 16.sp,
                    color = Color(0xFF442424),
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )

                Column(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = { onClick(isLock) }) {
                        Icon(
                            imageVector = Icons.Default.Update,
                            contentDescription = "icon_update",
                            modifier = modifier.size(25.dp)
                        )
                    }
                }


            }
        }
    }

}

