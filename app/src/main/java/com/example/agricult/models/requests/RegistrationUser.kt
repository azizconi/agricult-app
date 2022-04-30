package com.example.agricult.models.requests

import android.app.DatePickerDialog
import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class RegistrationUser(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("surname")
    val surname: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("date_of_birth")
    val date_of_birth: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("media")
    val media: Bitmap? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("confirm_password")
    val confirm_password: String? = null,
    @SerializedName("email")
    val email: String? = null
)