package com.example.agricult.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.agricult.models.category.CategoryModel
import com.example.agricult.models.category.Data
import com.example.agricult.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {


    private var setCategoryModel: List<Data> = emptyList()
    var getCategoryModel = mutableStateOf(setCategoryModel)


    fun getSearchAnnouncement(
        query: String,
        token: String,
        priceFrom: Int,
        priceTo: Int,
        page: Int,
        orderBy: String
    ) {
        return setSearchAnnouncement(
            query = query,
            token = token,
            priceFrom = priceFrom,
            priceTo = priceTo,
            page = page,
            orderBy = orderBy
        )
    }


    private fun setSearchAnnouncement(
        query: String,
        token: String,
        priceFrom: Int,
        priceTo: Int,
        page: Int,
        orderBy: String
    ) {
        RetrofitInstance().api().getSearchAnnouncement(
            token = token,
            query = query,
            priceFrom = priceFrom,
            priceTo = priceTo,
            page = page,
            orderBy = orderBy
        )
            .enqueue(object : Callback<CategoryModel> {
                override fun onResponse(
                    call: Call<CategoryModel>,
                    response: Response<CategoryModel>
                ) {

                    if (response.isSuccessful) {

                        response.body()?.data.let {
                            if (it != null) {
                                getCategoryModel.value = it
                            }
                        }

                    }

                }

                override fun onFailure(call: Call<CategoryModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

}