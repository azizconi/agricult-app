package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.agricult.models.errors.ErrorMessageLogin
import com.example.agricult.models.loginResult.AuthResult
import com.example.agricult.models.loginResult.Data
import com.example.agricult.models.requests.LoginUserModel
import com.example.agricult.models.requests.RegistrationUser
import com.example.agricult.network.RetrofitInstance
import com.google.gson.GsonBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class LoginViewModel(application: Application) : AndroidViewModel(application) {


    val isSuccessLoading = mutableStateOf(value = false)
    private val setErrorMessage = ErrorMessageLogin()
    val getErrorMessageLogin = mutableStateOf(setErrorMessage)

    private val setDataAgriculture = Data()
    val getDataAgriculture = mutableStateOf(setDataAgriculture)
    val checkInternet = mutableStateOf(value = false)


    fun getLoginRequest(
        authenticationUser: LoginUserModel,
        dataStoreViewModel: DataStoreViewModel
    ) {
        return setLoginRequest(authenticationUser, dataStoreViewModel)
    }

    private fun setLoginRequest(
        authenticationUser: LoginUserModel,
        dataStoreViewModel: DataStoreViewModel
    ) {
        RetrofitInstance().api().login(authenticationUser = authenticationUser)
            .enqueue(object : Callback<AuthResult> {
                override fun onResponse(call: Call<AuthResult>, response: Response<AuthResult>) {

                    if (response.code() != 200) {

                        val gson = GsonBuilder().create()
                        val pojo: ErrorMessageLogin
                        try {
                            pojo = gson.fromJson(
                                response.errorBody()!!.string(),
                                ErrorMessageLogin::class.java
                            )
                            getErrorMessageLogin.value = pojo
                            Log.e("TAG", "onResponse: ${getErrorMessageLogin.value.message}")

//                            errorMessageLogin.value = pojo
//                            errorMessageLogin.value?.errors?.phone?.forEach {
//                                Log.e("TAG", "onResponse номер телефона: ${pojo.errors?.phone?.size}", )
//                            }

                        } catch (e: IOException) {
                            // handle failure at error parse
                        }
                    }

                    if (response.isSuccessful) {


                        isSuccessLoading.value = true



                        response.body()?.data.let {
                            if (it != null) {
                                getDataAgriculture.value = it
                                dataStoreViewModel.saveToDataStore(it.accessToken.toString())
                            }


                        }


                    } else {
                        isSuccessLoading.value = false
                    }
                    Log.e("TAG", "Navigation: ${isSuccessLoading.value}")
                }

                override fun onFailure(call: Call<AuthResult>, t: Throwable) {
                    Log.e("TAG", t.message.toString())
                }

            })
    }


    fun getRegisterRequest(
        registrationUser: RegistrationUser,
        dataStoreViewModel: DataStoreViewModel
    ) {

        return setRegisterRequest(
            registrationUser = registrationUser,
            dataStoreViewModel = dataStoreViewModel
        )
    }


    private fun setRegisterRequest(
        registrationUser: RegistrationUser,
        dataStoreViewModel: DataStoreViewModel
    ) {
        RetrofitInstance().api().register(
            registrationUser = registrationUser
        )
            .enqueue(object : Callback<AuthResult> {
                override fun onResponse(call: Call<AuthResult>, response: Response<AuthResult>) {
                    if (response.isSuccessful) {
                        isSuccessLoading.value = true

                        response.body()?.let {
                            checkInternet.value = false


                        }

                        response.body()?.data.let {
                            if (it != null) {
                                dataStoreViewModel.saveToDataStore(it.accessToken.toString())
                            }
                        }
                    } else {
                        try {
                            val jObjError: JSONObject = JSONObject(response.errorBody().toString())
                            Log.e("TAG", "onResponse: $jObjError")
                        } catch (e: Exception) {
                            Log.e("TAG", "onResponse: ${e.message}")
                        }
                        isSuccessLoading.value = false
                    }
                }


                override fun onFailure(call: Call<AuthResult>, t: Throwable) {
                    Log.e("TAG", t.message.toString())

                    checkInternet.value = false
                }

            })
    }


}