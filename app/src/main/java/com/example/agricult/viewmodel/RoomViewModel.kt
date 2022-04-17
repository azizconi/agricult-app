package com.example.agricult.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.agricult.db.AgricultureDatabase
import com.example.agricult.models.loginResult.Data
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomViewModel(application: Application): AndroidViewModel(application) {

    private val daoAgriculture = AgricultureDatabase.getInstance(application).daoAgriculture()



    fun getToken() = daoAgriculture.getTokens()


    fun addToken(data: Data) = GlobalScope.launch {
        daoAgriculture.addToken(data = data)
    }



    fun deleteToken() = GlobalScope.launch{
        daoAgriculture.deleteToken()
    }



}