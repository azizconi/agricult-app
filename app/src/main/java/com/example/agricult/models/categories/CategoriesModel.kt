package com.example.agricult.models.categories

import com.google.gson.annotations.SerializedName

data class CategoriesModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("icon") val icon: String? = null,
    @SerializedName("quantity") val quantity: Int? = null,
    @SerializedName("created_at") val created_at: String? = null
)