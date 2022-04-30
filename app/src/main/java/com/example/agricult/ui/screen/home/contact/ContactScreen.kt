package com.example.agricult.ui.screen.home.contact

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.models.contacts.ContactModel
import com.example.agricult.models.contacts.Data
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.ContactViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun ContactScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    contactViewModel: ContactViewModel,
    dataStoreViewModel: DataStoreViewModel
) {


    val getToken = remember {
        mutableStateOf("")
    }

    val contactModel = contactViewModel.getContactAnnouncementModel.value

    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()



    contactViewModel.getContacts(getToken.value)


    Column {
        ContactToolbar(navHostController = navHostController)

        LazyColumn(
            modifier = modifier
                .padding(top = 5.dp)
        ) {
            items(contactModel.size) {
                ContactsPhoneItem(phone = contactModel[it].phone)
                ContactsEmailItem(email = contactModel[it].email)
            }
        }
    }


}

@Composable
fun ContactToolbar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,

    ) {

    TopAppBar(
        title = {
            Text(
                text = "Контакт центр",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                color = Color.White,
                textAlign = TextAlign.Center
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

}


@Composable
fun ContactsPhoneItem(
    modifier: Modifier = Modifier,
    phone: String
) {

    val context = LocalContext.current

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
            .clickable {
                openPhone(phone, context)
            }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {

            Image(
                painter = rememberCoilPainter(request = R.drawable.phone),
                contentDescription = "phone",
                modifier = modifier
                    .size(28.dp)
            )

            Spacer(
                modifier = modifier
                    .width(15.dp)
            )

            Row(
                modifier = modifier
                    .height(28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    text = phone,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color(0xff444444),
                    textAlign = TextAlign.Center,

                    )
            }
        }

    }

}


@Composable
fun ContactsEmailItem(
    modifier: Modifier = Modifier,
    email: String
) {
    val context = LocalContext.current

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
            .clickable {
                openEmail(email, context)
            }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {

            Image(
                painter = rememberCoilPainter(request = R.drawable.email),
                contentDescription = "email",
                modifier = modifier
                    .size(28.dp)
            )

            Spacer(
                modifier = modifier
                    .width(15.dp)
            )

            Row(
                modifier = modifier
                    .height(28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    text = email,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color(0xff444444),
                    textAlign = TextAlign.Center,

                    )
            }
        }
    }

}

fun openPhone(numberPhone: String, context: Context) {

    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$numberPhone")

    context.startActivity(intent)

}

fun openEmail(email: String, context: Context) {

    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:$email")

    context.startActivity(intent)
}