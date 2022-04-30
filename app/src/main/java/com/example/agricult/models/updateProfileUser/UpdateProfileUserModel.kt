package com.example.agricult.models.updateProfileUser

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

data class UpdateProfileUserModel(
    @SerializedName("name") val name: String? = null,
    @SerializedName("surname") val surname: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("date_of_birth") val date_of_birth: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("media") val media: List<File>? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("confirm_password") val confirm_password: String? = null,
    @SerializedName("email") val email: String? = null,
)
