package com.example.agricult.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agricult.models.requests.LoginUserModel
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextFieldColor
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    dataStoreViewModel: DataStoreViewModel,
) {



    val context = LocalContext.current

    var numberPhone by rememberSaveable {
        mutableStateOf("")
    }


    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var passwordVisibleChar by rememberSaveable { mutableStateOf(true) }

    var isVisibleProgressBar by rememberSaveable {
        mutableStateOf(false)
    }

    var colorIndicatorTextFieldPhoneNumber by remember {
        mutableStateOf(Color.Red)
    }


    var colorIndicatorTextFieldPassword by remember {
        mutableStateOf(Color.Red)
    }

    var enableInputs by remember {
        mutableStateOf(true)
    }

    val charPhoneNumber = 9
    val minCharPassword = 6



    enableInputs = isVisibleProgressBar == false




    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp),
    ) {

        Text(
            text = "Номер телефона",
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 8.dp, top = 10.dp)
        )

        OutlinedTextFieldBackground(TextFieldColor) {
            OutlinedTextField(
                singleLine = true,
                value = numberPhone,
                onValueChange = {
                    if (it.length <= charPhoneNumber) numberPhone = it

                    colorIndicatorTextFieldPhoneNumber = if (numberPhone.isNotEmpty()) {
                        PrimaryColorGreen
                    } else {
                        Color.Red
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = modifier
                    .fillMaxWidth()
                    .height(55.dp),
                leadingIcon = {
                    Text(text = "+992", fontSize = 16.sp)
                },
                textStyle = TextStyle(fontSize = 16.sp),

                enabled = enableInputs,
                colors = TextFieldDefaults
                    .outlinedTextFieldColors(
                        cursorColor = PrimaryColorGreen,
                        focusedBorderColor = colorIndicatorTextFieldPhoneNumber
                    )
            )
        }



        Text(
            text = "Пароль",
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 8.dp, top = 16.dp)
        )

        TextField(
            value = password,
            onValueChange = {
                passwordVisibleChar = it.length >= minCharPassword

                password = it


                colorIndicatorTextFieldPassword = if (!passwordVisibleChar)
                    Color.Red
                else
                    PrimaryColorGreen
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = modifier
                .fillMaxWidth()
                .height(55.dp),
            textStyle = TextStyle(fontSize = 16.sp),


            enabled = enableInputs,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = PrimaryColorGreen,
                disabledLabelColor = PrimaryColorGreen,
                focusedBorderColor = colorIndicatorTextFieldPassword
            ),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        description,
                        modifier = modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
            }
        )


        if (!passwordVisibleChar) {
            Text(text = "Пароль должен состоять из минимум 6 символов", color = Color.Red)
        }

        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(71.dp)
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorGreen),
            enabled = enableInputs,
            onClick = {


                if (numberPhone.isNotEmpty() && password.isNotEmpty()) {
                    if (password.length >= minCharPassword && numberPhone.length == charPhoneNumber) {


                        loginViewModel.getLoginRequest(
                            LoginUserModel(
                                "+992$numberPhone",
                                password
                            ),
                            dataStoreViewModel
                        )


                        isVisibleProgressBar = true


                    } else {
                        Toast.makeText(
                            context,
                            "Проверьте правильность полей",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Заполните поля",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        ) {
            Text(
                text = "Войти",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }





        if (isVisibleProgressBar) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {
                CircularProgressIndicator(
                    color = PrimaryColorGreen,
                )
            }
        }

        if (loginViewModel.isSuccessLoading.value) {


            loginViewModel.getDataAgriculture.value.isSuccessLoading =
                loginViewModel.isSuccessLoading.value



            loginViewModel.isSuccessLoading.value = false

        }

        if (!loginViewModel.checkInternet.value) {
            isVisibleProgressBar = false
        }


        if (loginViewModel.getErrorMessageLogin.value.message != null &&
            loginViewModel.getErrorMessageLogin.value.errors?.phone?.get(0) == null
        ) {
            Toast.makeText(
                context,
                loginViewModel.getErrorMessageLogin.value.message,
                Toast.LENGTH_LONG
            ).show()
            isVisibleProgressBar = if (!loginViewModel.isSuccessLoading.value) {
                false
            } else {
                false
            }
        } else if (loginViewModel.getErrorMessageLogin.value.message != null &&
            loginViewModel.getErrorMessageLogin.value.errors?.phone?.get(0) != null
        ) {

            Toast.makeText(
                context,
                loginViewModel.getErrorMessageLogin.value.errors?.phone?.get(0),
                Toast.LENGTH_LONG
            ).show()

            isVisibleProgressBar = if (loginViewModel.isSuccessLoading.value) {
                false
            } else {
                false
            }
        }


    }


}


@Composable
fun OutlinedTextFieldBackground(
    color: Color,
    content: @Composable () -> Unit
) {
    // This box just wraps the background and the OutlinedTextField
    Box {
        // This box works as background
        Box(
            modifier = Modifier
                .matchParentSize()
//                .padding(top = 8.dp) // adding some space to the label
                .background(
                    color,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(4.dp)
                )
        )
        // OutlineTextField will be the content...
        content()
    }
}

