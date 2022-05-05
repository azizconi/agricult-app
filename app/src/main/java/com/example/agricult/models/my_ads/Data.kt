package com.example.agricult.models.my_ads

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id") val id : Int? = null,
    @SerializedName("title") val title : String? = null,
    @SerializedName("images") val images : List<String>? = null,
    @SerializedName("price") val price : Double? = null,
    @SerializedName("description") val description : String? = null,
    @SerializedName("address") val address : String? = null,
    @SerializedName("phone") val phone : String? = null,
    @SerializedName("email") val email : String? = null,
    @SerializedName("category") val category : Category? = null,
    @SerializedName("user") val user : User? = null,
    @SerializedName("moderation_status") val moderation_status : ModerationStatus? = null,
    @SerializedName("created_at") val created_at : String? = null
)