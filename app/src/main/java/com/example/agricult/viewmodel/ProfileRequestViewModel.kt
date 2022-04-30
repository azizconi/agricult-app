package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.room.Update
import com.example.agricult.models.errors.ErrorMessageLogin
import com.example.agricult.models.profileShowResult.Data
import com.example.agricult.models.profileShowResult.ProfileShowResult
import com.example.agricult.models.updateProfileUser.UpdateProfileUserModel
import com.example.agricult.network.RetrofitInstance
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class ProfileRequestViewModel(application: Application) : AndroidViewModel(application) {

    private val setDataAgriculture = Data()
    val getShowUserData = mutableStateOf(setDataAgriculture)

    var isSuccessLoadingProfile = mutableStateOf(value = false)

    fun getShowProfileUser(token: String) {
        return setShowProfileUser(token)
    }

    private fun setShowProfileUser(token: String) {
        RetrofitInstance().api().getProfileShow(token = "Bearer $token")
            .enqueue(object : Callback<ProfileShowResult> {
                override fun onResponse(
                    call: Call<ProfileShowResult>,
                    response: Response<ProfileShowResult>
                ) {



                    if (response.code() != 200) {
//                        roomViewModel.deleteToken()

                        val gson = GsonBuilder().create()
                        val pojo: ErrorMessageLogin
                        try {
                            pojo = gson.fromJson(
                                response.errorBody()!!.string(),
                                ErrorMessageLogin::class.java
                            )
                        } catch (e: IOException) {
                            // handle failure at error parse
                        }

                    }

                    if(response.isSuccessful) {
                        response.body()?.data.let {
                            Log.e("TAG", "onResponse: $it", )
                            if (it != null) {
                                getShowUserData.value = it
                            }

                        }
                        isSuccessLoadingProfile.value = true
                    }

                }

                override fun onFailure(call: Call<ProfileShowResult>, t: Throwable) {
                    Log.e("TAG", t.message.toString(), )
                    isSuccessLoadingProfile.value = false
                }

            })
    }


    fun updateProfileRequest(token: String, updateProfileUserModel: UpdateProfileUserModel) {
        RetrofitInstance().api().updateUserProfileData(
            token = "Bearer $token",
            updateProfileUserModel = updateProfileUserModel
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.e("TAG", "onResponse: ${response.body()}", )
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("TAG", "onResponse: ${t.message}", )
            }

        })
    }

}