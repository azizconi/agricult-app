package com.example.agricult.ui.screen.home.user_info

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agricult.R
import com.example.agricult.models.profileShowResult.Data
import com.example.agricult.models.updateProfileUser.UpdateProfileUserModel
import com.example.agricult.ui.screen.login.OutlinedTextFieldBackground
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextFieldBorder
import com.example.agricult.ui.theme.TextFieldColor
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.ProfileRequestViewModel
import com.google.accompanist.coil.rememberCoilPainter
import java.io.File
import java.io.IOException
import java.util.*

@Composable
fun UserInfoScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    dataStoreViewModel: DataStoreViewModel,
    profileRequestViewModel: ProfileRequestViewModel
) {

    val getToken = remember {
        mutableStateOf("")
    }
    getToken.value = dataStoreViewModel.readFromDataStore.value.toString()

    val profileData = profileRequestViewModel.getShowUserData.value


    var changeData by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .padding(bottom = 56.dp)
    ) {
        UserInfoToolbar(
            navHostController = navHostController,
            changeData = changeData
        )
        FormUserInfo(
            data = profileData,
            profileRequestViewModel = profileRequestViewModel,
            getToken = getToken.value,
            navHostController = navHostController
        ) {
            changeData = it
        }
    }


}

@Composable
fun UserInfoToolbar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    changeData: Boolean
) {

    var openDialog by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
            Text(
                text = "Личная информация",
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
                    if (changeData) {
                        openDialog = true

                    } else {
                        navHostController.popBackStack()
                    }

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


    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Text(
                    text = "Вы действительно хотите выйти не сохраняя?",
                    color = Color.Black
                )
            },


            confirmButton = {

                TextButton(
                    onClick = {
                        openDialog = false
                        navHostController.popBackStack()

                    }) {
                    Text(text = "Да", color = PrimaryColorGreen)
                }

            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }) {
                    Text(text = "Нет", color = PrimaryColorGreen)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.White
        )
    }
}

@Composable
fun SelectPhotoUser(
    profileRequestViewModel: ProfileRequestViewModel,
    onSelectImage: (Uri) -> Unit
) {
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            selectedImage = uri
        }

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current



    PhotoProfile(
        profileRequestViewModel = profileRequestViewModel,
        selectedImage = selectedImage,
        onImageClick = {
            launcher.launch("image/*")

        },
        onSelectImage = {
            onSelectImage(it)
        }
    )
}



@Composable
fun PhotoProfile(
    modifier: Modifier = Modifier,
    profileRequestViewModel: ProfileRequestViewModel,
    selectedImage: Uri? = null,
    onImageClick: () -> Unit,
    onSelectImage: (Uri) -> Unit
) {

    val context = LocalContext.current


    val profileData = profileRequestViewModel.getShowUserData.value

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(173.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = modifier
                .size(107.dp)

        ) {

            if (selectedImage != null) {
                Image(
                    painter = rememberCoilPainter(request = selectedImage),
                    contentScale = ContentScale.Crop,
                    contentDescription = "user-photo",
                    modifier = modifier
                        .size(107.dp)
                        .clip(shape = RoundedCornerShape(600.dp))
                )
                onSelectImage(selectedImage)
            } else {
                Image(
                    painter = rememberCoilPainter(request = "http://api.agricult.colibri.tj/public/storage/${profileData.image}"),
                    contentScale = ContentScale.Crop,
                    contentDescription = "user-photo",
                    modifier = modifier
                        .size(107.dp)
                        .clip(shape = RoundedCornerShape(600.dp))
                )
            }


            Box(
                modifier = modifier
                    .size(25.dp)
                    .clip(shape = RoundedCornerShape(600.dp))
                    .background(Color(0xff27AE60))
                    .align(Alignment.BottomEnd)
                    .clickable {
                        onImageClick()
                    }
            ) {
                Image(
                    painter = rememberCoilPainter(request = R.drawable.camera),
                    contentDescription = "camera-icon",
                    modifier = modifier
                        .width(14.42.dp)
                        .height(13.37.dp)
                        .align(Alignment.Center)
                )
            }

        }

    }


}


@Composable
fun FormUserInfo(
    modifier: Modifier = Modifier,
    data: Data,
    profileRequestViewModel: ProfileRequestViewModel,
    getToken: String,
    navHostController: NavHostController,
    changedData: (Boolean) -> Unit
) {

    var imageProfile by remember {
        mutableStateOf<Uri?>(null)
    }

    var imageFile by remember {
        mutableStateOf<File?>(null)
    }


    var name by remember {
        mutableStateOf(data.name)
    }

    var surname by remember {
        mutableStateOf(data.surname)
    }

    var numberPhone by remember {
        mutableStateOf(data.phone)
    }

    var date by remember {
        mutableStateOf(data.date_of_birth)
    }

    var address by remember {
        mutableStateOf(data.address)
    }

    val charPhoneNumber = 9

    val year: Int
    val month: Int
    val day: Int
    val context = LocalContext.current


    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    var filterMonth = ""

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

            filterMonth = if (month in 1..9) {
                "0"
            } else {
                ""
            }


            date = "$year-${filterMonth + (month + 1)}-$dayOfMonth"
        }, year, month, day

    )

    var dataChange by remember {
        mutableStateOf(false)
    }


    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {

        item {
            SelectPhotoUser(profileRequestViewModel = profileRequestViewModel) {
                imageProfile = it




                imageFile = File(imageProfile.toString())
                Log.e("TAG", "FormUserInfo: ${imageFile}")

            }
        }

        item {
            Text(
                text = "Имя",
                fontSize = 16.sp,
                modifier = modifier
                    .padding(bottom = 8.dp, top = 10.dp)
            )

            OutlinedTextFieldBackground(TextFieldColor) {
                OutlinedTextField(
                    value = name.toString(),
                    onValueChange = {
                        name = it
                    },

                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    textStyle = TextStyle(fontSize = 16.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = PrimaryColorGreen,
                        focusedBorderColor = PrimaryColorGreen,
                        unfocusedBorderColor = TextFieldBorder
                    ),
                    maxLines = 1
                )
            }
        }



        item {
            Text(
                text = "Фамилия",
                fontSize = 16.sp,
                modifier = modifier
                    .padding(bottom = 8.dp, top = 10.dp)
            )

            OutlinedTextFieldBackground(TextFieldColor) {

                OutlinedTextField(
                    value = surname.toString(),
                    onValueChange = {
                        surname = it
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    textStyle = TextStyle(fontSize = 16.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = PrimaryColorGreen,
                        focusedBorderColor = PrimaryColorGreen,
                        unfocusedBorderColor = TextFieldBorder
                    ),
                )
            }
        }

        item {
            Text(
                text = "Номер телефона",
                fontSize = 16.sp,
                modifier = modifier
                    .padding(bottom = 8.dp, top = 10.dp)
            )

            OutlinedTextFieldBackground(TextFieldColor) {

                OutlinedTextField(
                    singleLine = true,
                    value = numberPhone.toString(),
                    onValueChange = {
                        if (charPhoneNumber <= 9) {
                            numberPhone = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    textStyle = TextStyle(fontSize = 16.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = PrimaryColorGreen,
                        focusedBorderColor = PrimaryColorGreen,
                        unfocusedBorderColor = TextFieldBorder
                    ),
                    maxLines = 1
                )
            }
        }



        item {
            Text(
                text = "День рождения (ДД/ММ/ГГГГ)",
                fontSize = 16.sp,
                modifier = modifier
                    .padding(bottom = 8.dp, top = 10.dp)
            )
            OutlinedTextFieldBackground(TextFieldColor) {

                OutlinedTextField(
                    value = date.toString(),
                    onValueChange = {
                        date = it
                    },
                    enabled = false,

                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clickable {
                            datePickerDialog.show()

                        },

                    textStyle = TextStyle(fontSize = 16.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = PrimaryColorGreen,
                        focusedBorderColor = PrimaryColorGreen,
                        unfocusedBorderColor = TextFieldBorder
                    ),

                    trailingIcon = {

                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            "Date",
                            modifier = modifier
                                .width(25.dp)
                                .height(25.dp)
                        )

                    },
                )
            }
        }



        item {
            Text(
                text = "Место жительство",
                fontSize = 16.sp,
                modifier = modifier
                    .padding(bottom = 8.dp, top = 10.dp)
            )

            OutlinedTextFieldBackground(TextFieldColor) {


                OutlinedTextField(
                    value = address.toString(),
                    onValueChange = {
                        address = it
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    textStyle = TextStyle(fontSize = 16.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = PrimaryColorGreen,
                        focusedBorderColor = PrimaryColorGreen,
                        unfocusedBorderColor = TextFieldBorder
                    ),
                )
            }
        }


        item {
            Box(
                modifier = modifier
                    .padding(vertical = 32.dp)
            ) {

                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorGreen),
                    onClick = {
                        if (imageFile != null) {
                            val updateDataUserProfile = UpdateProfileUserModel(
                                name = name,
                                surname = surname,
                                phone = numberPhone,
                                date_of_birth = date,
                                address = address,
                                media = listOf(
                                    getFileFromPath(
                                        uri = imageProfile!!,
                                        name = imageProfile?.path.toString(),
                                        context = context
                                    )
                                ),
                            )

                            profileRequestViewModel.updateProfileRequest(
                                token = getToken,
                                updateProfileUserModel = updateDataUserProfile
                            )

                            navHostController.popBackStack()
                        } else {
                            val updateDataUserProfile = UpdateProfileUserModel(
                                name = name,
                                surname = surname,
                                phone = numberPhone,
                                date_of_birth = date,
                                address = address,
                            )

                            profileRequestViewModel.updateProfileRequest(
                                token = getToken,
                                updateProfileUserModel = updateDataUserProfile
                            )

                            navHostController.popBackStack()
                        }


                    }
                ) {
                    Text(
                        text = "Сохранить",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    )
                }

            }
        }


    }

    dataChange = name != data.name ||
            surname != data.surname ||
            numberPhone != data.phone ||
            date != data.date_of_birth ||
            address != data.address

    changedData(dataChange)


}


fun decodeUriToBitmap(context: Context, uri: Uri?): Bitmap {
    return uri?.let { uriPath ->
        context.contentResolver.openInputStream(uriPath).use { stream ->
            BitmapFactory.decodeStream(stream)
        }
    } ?: throw IOException("Image not found.")
}

private fun File.writeBitmap(bitmap: Bitmap) {
    outputStream().use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)
        out.flush()
    }
}

fun getFileFromPath(uri: Uri, name: String, context: Context): File {
    val bitmap = decodeUriToBitmap(context, uri)
    val file = File("${context.cacheDir}", "$name.jpeg")
    file.writeBitmap(bitmap)
    return file
}

