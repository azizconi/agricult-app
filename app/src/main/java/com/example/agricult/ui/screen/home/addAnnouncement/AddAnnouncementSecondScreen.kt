package com.example.agricult.ui.screen.home.addAnnouncement

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.models.addAds.AddAds
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextFieldColor
import com.google.accompanist.coil.rememberCoilPainter

@SuppressLint("ResourceType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddAnnouncementSecondScreen(
    modifier: Modifier = Modifier,
    data: AddAds? = null,
    navHostController: NavHostController
) {

    var imageUrl by remember { mutableStateOf<Uri?>(null) }

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }





    Log.e("TAG", "AddAnnouncementSecondScreen: ${data?.address}")


    val labelTextColor by remember {
        mutableStateOf(Color(0xff444444))
    }


    var descriptionAnnouncement by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var countImages by remember {
        mutableStateOf(0)
    }


    val imageList: List<Bitmap> = listOf()
    var imageListInit by remember {
        mutableStateOf(imageList)
    }

    var imageOne by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var imageTwo by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var imageThree by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var imageFour by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var imageFive by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var imageSix by remember {
        mutableStateOf<Bitmap?>(null)
    }




    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
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

        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

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


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(256.dp)


                            .border(1.dp, Color(0xffD4D4D4), shape = RoundedCornerShape(4.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        imageUrl?.let {
                            PickImageFromGallery(
                                context = context,
                                bitmap = bitmap,
                                imageUrl = it
                            )
                        }



                        Image(
                            painter = rememberCoilPainter(request = R.drawable.addphoto),
                            contentDescription = "add photo",
                            modifier = Modifier
                                .size(56.dp)
                                .clickable { launcher.launch("image/*") }
                        )

                    }


                }


                Log.e("TAG", "countImages: $countImages")
                Log.e("TAG", "imageListInit: ${imageListInit.size}")

            }


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

            Column(
                modifier = modifier
                    .padding(bottom = 62.dp, top = 11.dp)
                    .fillMaxWidth()

            ) {

                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorGreen),
                    onClick = {


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


@Composable
fun PickImageFromGallery(
    imageUrl: Uri,
    bitmap: MutableState<Bitmap?>,
    context: Context
) {


    imageUrl.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }


        bitmap.value?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Gallery Image",
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }


}

@Composable
fun PickImageFromGalleryBottom(
    modifier: Modifier = Modifier,
    convertData: (bitmapImage: Bitmap) -> Unit,
) {

    val context = LocalContext.current

    var imageUrl by remember { mutableStateOf<Uri?>(null) }

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }





    Column(
        modifier = modifier
            .width(100.dp)
            .height(100.dp)

            .border(
                1.dp,
                Color(0xffD4D4D4),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        imageUrl?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)

            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }


            bitmap.value?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Gallery Image",
                    modifier = Modifier
                        .padding(10.dp)
                )

                Log.e("TAG", "PickImageFromGalleryBottom: ${bitmap.asImageBitmap()}",)
            }
        }

        if (bitmap.value != null) {
            convertData(bitmap.value!!)
        }

        Image(
            painter = rememberCoilPainter(request = R.drawable.addphoto),
            contentDescription = "add photo",
            modifier = Modifier
                .size(36.dp)
                .clickable { launcher.launch("image/*") }
        )

    }


}

