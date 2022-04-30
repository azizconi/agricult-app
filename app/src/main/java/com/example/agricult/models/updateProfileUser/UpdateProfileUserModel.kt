package com.example.agricult.models.updateProfileUser

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import java.io.File

data class UpdateProfileUserModel(
    @Part
    @SerializedName("name")
    val name: String? = null,

    @Part
    @SerializedName("surname")
    val surname: String? = null,

    @Part
    @SerializedName("phone")
    val phone: String? = null,

    @Part
    @SerializedName("date_of_birth")
    val date_of_birth: String? = null,

    @Part
    @SerializedName("address")
    val address: String? = null,

    @Part
    @SerializedName("media")
    val media: MultipartBody.Part? = null,

    @Part
    @SerializedName("password")
    val password: String? = null,

    @Part
    @SerializedName("confirm_password")
    val confirm_password: String? = null,

    @Part
    @SerializedName("email")
    val email: String? = null,
)
