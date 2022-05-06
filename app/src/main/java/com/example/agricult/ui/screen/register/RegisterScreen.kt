package com.example.agricult.ui.screen.register

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.agricult.models.requests.RegistrationUser
import com.example.agricult.ui.screen.login.OutlinedTextFieldBackground
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.ui.theme.TextFieldColor
import com.example.agricult.viewmodel.DataStoreViewModel
import com.example.agricult.viewmodel.LoginViewModel
import java.util.*


@Composable
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    dataStoreViewModel: DataStoreViewModel
) {

    var name by remember {
        mutableStateOf("")
    }

    var surname by remember {
        mutableStateOf("")
    }

    var numberPhone by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var passwordVisibleChar by rememberSaveable { mutableStateOf(true) }

    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }

    val charPhoneNumber = 9
    val minCharPassword = 8

    var colorIndicatorTextFieldPassword by remember {
        mutableStateOf(Color.Red)
    }


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

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->

            filterMonth = if (month in 1..9) {
                "0"
            } else {
                ""
            }


            date.value = "$year-${filterMonth + (month + 1)}-$dayOfMonth"
        }, year, month, day

    )



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        Text(
            text = "Имя",
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 8.dp, top = 10.dp)
        )

        OutlinedTextFieldBackground(TextFieldColor) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(55.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = PrimaryColorGreen,
                    focusedBorderColor = PrimaryColorGreen
                ),
            )
        }

        Text(
            text = "Фамилия",
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 8.dp, top = 10.dp)
        )

        OutlinedTextFieldBackground(TextFieldColor) {

            OutlinedTextField(
                value = surname,
                onValueChange = {
                    surname = it
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(55.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = PrimaryColorGreen,
                    focusedBorderColor = PrimaryColorGreen
                ),
            )
        }

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
                    if (it.length <= charPhoneNumber) {
                        numberPhone = it
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = PrimaryColorGreen,
                    focusedBorderColor = PrimaryColorGreen
                ),
            )
        }


        Text(
            text = "День рождения (ДД/ММ/ГГГГ)",
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 8.dp, top = 10.dp)
        )
        OutlinedTextFieldBackground(TextFieldColor) {

            OutlinedTextField(
                value = date.value,
                onValueChange = {
                    date.value = it
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
                    focusedBorderColor = PrimaryColorGreen
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

        Text(
            text = "Место жительство",
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 8.dp, top = 10.dp)
        )

        OutlinedTextFieldBackground(TextFieldColor) {


            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(55.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = PrimaryColorGreen,
                    focusedBorderColor = PrimaryColorGreen
                ),
            )
        }



        Text(
            text = "Пароль",
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 8.dp, top = 16.dp)
        )

        OutlinedTextFieldBackground(TextFieldColor) {

            OutlinedTextField(
                value = password,
                onValueChange = {
                    passwordVisibleChar = it.length >= minCharPassword

                    password = it

                    if (it.isEmpty()) {
                        passwordVisibleChar = true
                    }


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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = PrimaryColorGreen,
                    focusedBorderColor = PrimaryColorGreen
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
        }


        if (!passwordVisibleChar) {
            Text(text = "Пароль должен состоять из минимум 8 символов", color = Color.Red)
        }


        Text(
            text = "Повторите пароль",
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 8.dp, top = 16.dp)
        )

        OutlinedTextFieldBackground(TextFieldColor) {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = modifier
                    .fillMaxWidth()
                    .height(55.dp),

                textStyle = TextStyle(fontSize = 16.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = PrimaryColorGreen,
                    focusedBorderColor = PrimaryColorGreen
                ),
            )
        }

        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(71.dp)
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorGreen),
            onClick = {
                if (name == "" || name.isEmpty() &&
                    surname == "" || surname.isEmpty() &&
                    numberPhone == "" || numberPhone.isEmpty() && numberPhone.length != charPhoneNumber &&
                    date.value == "" || date.value.isEmpty() &&
                    address == "" || address.isEmpty() &&
                    password == "" || password.isEmpty() &&
                    confirmPassword == "" || confirmPassword.isEmpty()
                ) {
                    Toast.makeText(
                        context,
                        "Заполните поля",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (password == confirmPassword) {
                        loginViewModel.getRegisterRequest(
                            RegistrationUser(
                                name = name,
                                surname = surname,
                                phone = "+992$numberPhone",
                                date_of_birth = date.value,
                                address = address,
                                password = password,
                                confirm_password = confirmPassword,
                            ),
                            dataStoreViewModel = dataStoreViewModel
                        )


                    } else {
                        Toast.makeText(
                            context,
                            "Пароли не совпадают",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        ) {
            Text(
                text = "Зарегистрироваться",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }


    }
}