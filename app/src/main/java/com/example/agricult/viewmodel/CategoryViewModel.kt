package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.agricult.models.categories.CategoriesModel
import com.example.agricult.models.category.CategoryModel
import com.example.agricult.models.category.Data
import com.example.agricult.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(application: Application): AndroidViewModel(application) {

    private var setCategoryModel: List<Data> = emptyList()
    var getCategoryModel = mutableStateOf(setCategoryModel)


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
                        response.body()?.let {

                            getCategoryModel.value = it.data
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