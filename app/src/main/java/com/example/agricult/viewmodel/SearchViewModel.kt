package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.agricult.models.search.Data
import com.example.agricult.models.search.SearchModel
import com.example.agricult.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {


    private var setSearchModel: List<Data> = emptyList()
    var getSearchModel = mutableStateOf(setSearchModel)


    fun getSearchAnnouncement(
        query: String,
        token: String,
        priceFrom: Int,
        priceTo: Int,
        page: Int,
        orderBy: String,
        dataStoreViewModel: DataStoreViewModel
    ) {
        return setSearchAnnouncement(
            query = query,
            token = token,
            priceFrom = priceFrom,
            priceTo = priceTo,
            page = page,
            orderBy = orderBy,
            dataStoreViewModel = dataStoreViewModel
        )
    }


    private fun setSearchAnnouncement(
        query: String,
        token: String,
        priceFrom: Int,
        priceTo: Int,
        page: Int,
        orderBy: String,
        dataStoreViewModel: DataStoreViewModel
    ) {
        RetrofitInstance().api().getSearchAnnouncement(
            token = "Bearer $token",
            query = query,
            priceFrom = priceFrom,
            priceTo = priceTo,
            page = page,
            orderBy = orderBy
        )
            .enqueue(object : Callback<SearchModel> {
                override fun onResponse(
                    call: Call<SearchModel>,
                    response: Response<SearchModel>
                ) {

                    if (response.code() == 401) {
                        dataStoreViewModel.clearDataStore()
                    }


                    if (response.isSuccessful) {

                        response.body()?.let {

//                            getCategoryModel.value = it.data
                            getSearchModel.value = it.result.data

                        }

                    }

                }

                override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }

            })
    }

}