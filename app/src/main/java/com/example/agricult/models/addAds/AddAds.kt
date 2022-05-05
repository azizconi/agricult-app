package com.example.agricult.models.addAds

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody


data class AddAds(
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("category_id")
    val categoryId: Int? = null,
    @SerializedName("user_id")
    val userId: Int? = null,
    @SerializedName("moderation_status_id")
    val moderationStatusId: Int = 1,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("email")
    val email: String? = null,
//    @SerializedName("media")
    val media: RequestBody? = null
)