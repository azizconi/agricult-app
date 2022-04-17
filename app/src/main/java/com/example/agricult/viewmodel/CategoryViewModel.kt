package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.agricult.models.category.CategoryModel
import com.example.agricult.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(application: Application): AndroidViewModel(application) {


    fun getCategoryRequest(token: String, categoryId: Int?) {
        return setCategoryRequest(token = token, categoryId = categoryId!!)
    }


    private fun setCategoryRequest(token: String, categoryId: Int) {
        RetrofitInstance().api().getCategory(token = "Bearer $token", categoryId = categoryId)
            .enqueue(object : Callback<CategoryModel>{
                override fun onResponse(
                    call: Call<CategoryModel>,
                    response: Response<CategoryModel>
                ) {

                    if (response.isSuccessful) {
                        response.body()?.data?.forEach {
                            Log.e("TAG", "onResponse: $it", )
                        }
                    }

                }

                override fun onFailure(call: Call<CategoryModel>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}", )
                }

            })
    }

}