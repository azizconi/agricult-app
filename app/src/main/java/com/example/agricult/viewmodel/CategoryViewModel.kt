package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.agricult.models.addAds.AddAds
import com.example.agricult.models.ads.AdsModel
import com.example.agricult.models.ads.Advertisement
import com.example.agricult.models.category.CategoryModel
import com.example.agricult.models.category.Data
import com.example.agricult.models.my_ads.MyAdsModel
import com.example.agricult.network.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private var setCategoryModel: List<Data> = emptyList()
    var getCategoryModel = mutableStateOf(setCategoryModel)


    private var setAdsModel: Advertisement? = null

    val getAdsModel = mutableStateOf(setAdsModel)

//    var getMyAds = mutableStateOf(com.example.agricult.models.my_ads.Data())

    private var setMyAds: com.example.agricult.models.my_ads.Data? = null

    var getMyAdsModel by mutableStateOf(setMyAds)


    fun getCategoryRequest(
        token: String,
        categoryId: Int,
        priceFrom: Int? = null,
        priceTo: Int? = null,
        page: Int? = null,
        orderBy: String? = null,
        dataStoreViewModel: DataStoreViewModel
    ) {
        return setCategoryRequest(
            token = token,
            categoryId = categoryId,
            priceFrom = priceFrom,
            priceTo = priceTo,
            page = page,
            orderBy = orderBy,
            dataStoreViewModel = dataStoreViewModel
        )
    }


    private fun setCategoryRequest(
        token: String,
        categoryId: Int,
        priceFrom: Int? = null,
        priceTo: Int? = null,
        page: Int? = null,
        orderBy: String? = null,
        dataStoreViewModel: DataStoreViewModel
    ) {

        RetrofitInstance().api().getCategory(
            token = "Bearer $token",
            categoryId = categoryId,
            priceFrom = priceFrom,
            priceTo = priceTo,
            page = page,
            orderBy = orderBy.toString(),
        )
            .enqueue(object : Callback<CategoryModel> {
                override fun onResponse(
                    call: Call<CategoryModel>,
                    response: Response<CategoryModel>
                ) {

                    if (response.code() == 401) {
                        dataStoreViewModel.clearDataStore()
                    }

                    if (response.isSuccessful) {
                        response.body()?.let {

                            getCategoryModel.value = it.data
                            Log.e("TAG", "onResponse: $it")


                        }
                    }

                }

                override fun onFailure(call: Call<CategoryModel>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }

            })

    }


    fun userAdsRequest(token: String, dataStoreViewModel: DataStoreViewModel) {
        RetrofitInstance().api().getUserAds(token = "Bearer $token")
            .enqueue(object : Callback<CategoryModel> {
                override fun onResponse(
                    call: Call<CategoryModel>,
                    response: Response<CategoryModel>
                ) {

                    if (response.code() == 401) {
                        dataStoreViewModel.clearDataStore()
                    }

                    if (response.isSuccessful) {
                        response.body()?.let {

                            getCategoryModel.value = it.data

                        }
                    }

                }

                override fun onFailure(call: Call<CategoryModel>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }

            })
    }


    fun getAdsById(token: String, id: Int, dataStoreViewModel: DataStoreViewModel) {
        RetrofitInstance().api().getAdsById(
            "Bearer $token",
            id = id
        ).enqueue(object : Callback<AdsModel> {
            override fun onResponse(call: Call<AdsModel>, response: Response<AdsModel>) {

                if (response.code() == 401) {
                    dataStoreViewModel.clearDataStore()
                }

                if (response.isSuccessful) {

                    response.body()?.advertisement.let {
                        getAdsModel.value = it
                    }

                }


            }

            override fun onFailure(call: Call<AdsModel>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
    }


    fun addAdsRequest(
        token: String,
        requestBody: RequestBody,
        category_id: Int,
        user_id: Int,
        moderation_status_id: Int,
        media: MultipartBody.Part
    ) {
        RetrofitInstance().api().addAds(
            token = "Bearer $token",
            requestBody = requestBody,
            moderation_status_id = moderation_status_id,
            user_id = user_id,
            category_id = category_id,
            media = media,
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {



                if (response.isSuccessful) {
                    response.body().let {
                        Log.e("TAG", "onResponse: $it")
                    }
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
    }


    fun getMyAds(
        token: String,
        id: Int,
        dataStoreViewModel: DataStoreViewModel
    ) {
        RetrofitInstance().api().getMyAds(
            "Bearer $token",
            id = id,
        ).enqueue(object : Callback<MyAdsModel> {
            override fun onResponse(call: Call<MyAdsModel>, response: Response<MyAdsModel>) {

                if (response.code() == 401) {
                    dataStoreViewModel.clearDataStore()
                }

                if (response.isSuccessful) {

                    response.body()?.let {

                        getMyAdsModel = it.data

                    }

                }

            }

            override fun onFailure(call: Call<MyAdsModel>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
    }


    fun updateAds(token: String, id: Int, addAds: AddAds, dataStoreViewModel: DataStoreViewModel) {
        RetrofitInstance().api().updateAds(token = "Bearer $token", id = id, addAds = addAds)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.code() == 401) {
                        dataStoreViewModel.clearDataStore()
                    }

                    if (response.isSuccessful) {
                        response.body().let {
                            Log.e("TAG", "onResponse: $it", )
                        }

                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}", )
                }

            })
    }

}