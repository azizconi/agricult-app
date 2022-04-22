package com.example.agricult.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.agricult.dataStore.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application): AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)

    val readFromDataStore = repository.accessToken.asLiveData()


    fun clearDataStore() = viewModelScope.launch(Dispatchers.IO) {
        repository.clearDataStore()
    }


    fun saveToDataStore(name: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToDataStore(name = name)
    }

}