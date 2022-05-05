package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.agricult.models.category.CategoryModel
import com.example.agricult.models.category.Data
import com.example.agricult.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {


    private var setFavouriteAnnouncement: List<Data> = emptyList()
    var getFavouriteAnnouncementModel = mutableStateOf(setFavouriteAnnouncement)


    fun getFavouritesAnnouncement(token: String, dataStoreViewModel: DataStoreViewModel) {
        return setFavouritesAnnouncement(token = token, dataStoreViewModel = dataStoreViewModel)
    }


    private fun setFavouritesAnnouncement(token: String, dataStoreViewModel: DataStoreViewModel) {
        RetrofitInstance().api().getFavouritesAnnouncement(token = "Bearer $token")
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

                            getFavouriteAnnouncementModel.value = it.data
                            Log.e("TAG", "onResponse: ${it.data.size}")


                        }

                    }

                }

                override fun onFailure(call: Call<CategoryModel>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }

            })
    }


    fun favoritesStore(token: String, id: Int) {


        RetrofitInstance().api().favoritesStore(token = "Bearer $token", favorite_id = id)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {


                    if (response.isSuccessful) {
                        response.body().let {
                            Log.e("TAG", "onResponse favoritesStore: $it")
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("TAG", "onFailure favoritesStore: ${t.message}")
                }

            })
    }

    fun deleteFavoriteStore(token: String, id: Int) {
        RetrofitInstance().api().deleteFavoritesStore(token = "Bearer $token", favoriteId = id)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.isSuccessful) {
                        response.body().let {
                            Log.e("TAG", "onResponse deleteFavoritesStore: $it", )
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("TAG", "onFailure deleteFavoritesStore: ${t.message}", )
                }

            })


    }

}