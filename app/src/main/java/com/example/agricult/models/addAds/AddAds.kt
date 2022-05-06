package com.example.agricult.models.addAds

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody


data class AddAds(

    val price: RequestBody? = null,
    val description: RequestBody? = null,
    val address: RequestBody? = null,
    val phone: RequestBody? = null,
    val categoryId: RequestBody? = null,
    val title: RequestBody? = null,
    val email: RequestBody? = null,
    val media: List<MultipartBody.Part>? = null
)