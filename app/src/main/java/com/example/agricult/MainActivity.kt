package com.example.agricult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.agricult.ui.screen.navigation.Navigation
import com.example.agricult.viewmodel.*

class MainActivity : ComponentActivity() {
    private val requestViewModel by viewModels<RequestViewModel>()
    private val roomViewModel by viewModels<RoomViewModel>()
    private val profileRequestViewModel by viewModels<ProfileRequestViewModel>()
    private val categoriesViewModel by viewModels<CategoriesViewModel>()
    private val categoryViewModel by viewModels<CategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Navigation(
                requestViewModel = requestViewModel,
                roomViewModel = roomViewModel,
                profileRequestViewModel = profileRequestViewModel,
                categoriesViewModel = categoriesViewModel,
                categoryViewModel = categoryViewModel
            )
        }

//        if (requestViewModel.isSuccessLoading.value) {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }
    }

//    override fun onStart() {
//        if (requestViewModel.isSuccessLoading.value) {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }
//
//        super.onStart()
//    }


}





