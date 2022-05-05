package com.example.agricult.ui.screen.home.addAnnouncement

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.bumptech.glide.util.Util
import com.example.agricult.R
import com.example.agricult.models.addAds.AddAds
import com.example.agricult.ui.screen.home.user_info.getFileFromPath
import com.example.agricult.ui.screen.home.user_info.rememberGetContentActivityResult
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextFieldColor
import com.example.agricult.viewmodel.CategoryViewModel
import com.example.agricult.viewmodel.DataStoreViewModel
import com.google.accompanist.coil.rememberCoilPainter
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

@SuppressLint("ResourceType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddAnnouncementSecondScreen(
    modifier: Modifier = Modifier,
    data: AddAds? = null,
    navHostController: NavHostController,
    categoryViewModel: CategoryViewModel,
    dataStoreViewModel: DataStoreViewModel
) {

    val getToken = remember {
        mutableStateOf("")
    }
    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()

    val labelTextColor by remember {
        mutableStateOf(Color(0xff444444))
    }


    var descriptionAnnouncement by rememberSaveable {
        mutableStateOf("")
    }


    val context = LocalContext.current

    val getContentActivityResult = rememberGetContentActivityResult()
    val file =
        getContentActivityResult.uri?.let { uri -> getFileFromPath(uri, "profilePicture", context) }

    val requestBody = file?.asRequestBody("image/*".toMediaType())
    val imageMultipart = requestBody?.let { createFormData("media", file.name, it) }



    Column(
        modifier = modifier
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Подать объявление",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color.White,

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

        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            item {
                Column(
                    modifier = modifier
                        .padding(top = 32.dp, bottom = 24.dp)
                ) {
                    Text(
                        text = "Дополнительные информация",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = Color(0xff333333)
                    )


                }
            }

            item {
                Column(
                    modifier = modifier
                        .padding(bottom = 24.dp)
                ) {

                    Text(
                        text = "Выбрать фото",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        color = labelTextColor,
                        modifier = modifier
                            .padding(bottom = 5.dp)
                    )


                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(256.dp)
                                .border(1.dp, Color(0xffD4D4D4), shape = RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = rememberCoilPainter(request = R.drawable.addphoto),
                                contentDescription = "add photo",
                                modifier = Modifier
                                    .size(56.dp)
                                    .clickable { getContentActivityResult.launch("image/*") }
                            )

                            Image(
                                painter = rememberImagePainter(
                                    data = getContentActivityResult.uri,
                                    builder = {
                                        Box(
                                            modifier = modifier
                                                .size(50.dp)
                                        ) {
                                            placeholder(R.drawable.ic_baseline_image_24)
                                        }
                                    }),
                                contentScale = ContentScale.Crop,
                                contentDescription = "ads-photo",
                                modifier = modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(4.dp))
                                    .padding(1.dp)
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = modifier
                        .padding(bottom = 62.dp)
                ) {

                    Text(
                        text = "Описание",
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
                            .border(1.dp, Color(0xffD4D4D4), shape = RoundedCornerShape(4.dp)),
                    ) {

                        BasicTextField(
                            modifier = modifier
                                .background(
                                    TextFieldColor,
                                    MaterialTheme.shapes.small
                                )
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 186.dp),
                            value = descriptionAnnouncement,
                            onValueChange = {
                                descriptionAnnouncement = it
                            },
                            singleLine = false,
                            cursorBrush = SolidColor(MaterialTheme.colors.primary),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color(0xff444444),
                                fontSize = 14.sp
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
            }

            item {
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
            }

            item {
                Column(
                    modifier = modifier
                        .padding(bottom = 62.dp, top = 11.dp)
                        .fillMaxWidth()

                ) {

                    Log.e("TAG", "AddAnnouncementSecondScreen: ${data?.categoryId}")

                    Button(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorGreen),
                        onClick = {
                            if (imageMultipart != null) {

                                val requestBodyData: RequestBody = MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("title", data?.title.toString())
                                    .addFormDataPart("description", data?.description.toString())
                                    .addFormDataPart("phone", data?.phone.toString())
                                    .addFormDataPart("price", data?.price.toString())
                                    .addFormDataPart("address", data?.address.toString())
//                                    .addPart(imageMultipart)
                                    .build()


                                if (data != null) {
                                    categoryViewModel.addAdsRequest(
                                        token = getToken.value,
                                        category_id = data.categoryId!!,
                                        user_id = data.userId!!,
                                        moderation_status_id = data.moderationStatusId,
                                        requestBody = requestBodyData,
                                        media = imageMultipart
                                    )

                                }

                            }
                        }
                    ) {
                        Text(
                            text = "Сохранить",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }


        }

    }
}