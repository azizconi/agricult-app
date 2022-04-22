package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.agricult.models.categories.CategoriesModel
import com.example.agricult.models.profileShowResult.Data
import com.example.agricult.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel(application: Application): AndroidViewModel(application) {

    private var setCategoriesModel: List<CategoriesModel> = emptyList()
    var getCategoriesModel = mutableStateOf(setCategoriesModel)

    fun getCategoriesRequest(token: String, dataStoreViewModel: DataStoreViewModel) {
        return setCategoriesRequest(token = token, dataStoreViewModel = dataStoreViewModel)
    }

    private fun setCategoriesRequest(token: String, dataStoreViewModel: DataStoreViewModel){
        RetrofitInstance().api().getCategories(token = "Bearer $token")
            .enqueue(object : Callback<List<CategoriesModel>> {
                override fun onResponse(
                    call: Call<List<CategoriesModel>>,
                    response: Response<List<CategoriesModel>>
                ) {

                    if (response.code() == 401) {
                        dataStoreViewModel.clearDataStore()
                    }


                    if (response.isSuccessful) {
                        response.body()?.let {
                            getCategoriesModel.value = it


                        }
                    }
                }

                override fun onFailure(call: Call<List<CategoriesModel>>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}", )
                }

            })
    }

}