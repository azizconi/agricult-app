package com.example.agricult.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agricult.ui.screen.navigation.Screen
import com.example.agricult.ui.theme.PrimaryColorGreen
import com.example.agricult.viewmodel.LoginViewModel


@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel
) {

    loginViewModel.isSuccessLoading.value = false

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColorGreen),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Добро пожаловать",
            fontSize = 45.sp,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(14.dp),
            color = Color.White
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                onClick = { navController.navigate(Screen.LoginScreen.route) }
            ) {
                Text(
                    text = "Войти",
                    textAlign = TextAlign.Center,
                    color = PrimaryColorGreen
                )
            }


            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                onClick = { navController.navigate(Screen.RegisterScreen.route) }
            ) {
                Text(
                    text = "Зарегистрироваться",
                    textAlign = TextAlign.Center,
                    color = PrimaryColorGreen)
            }
        }
    }

}

