package com.example.agricult.models.ads

import com.google.gson.annotations.SerializedName


data class Likes (

    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("images") val images : String,
    @SerializedName("price") val price : Double,
    @SerializedName("description") val description : String,
    @SerializedName("address") val address : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("email") val email : String,
    @SerializedName("category") val category : Category,
    @SerializedName("user") val user : User,
    @SerializedName("moderation_status") val moderation_status : Moderation_status,
    @SerializedName("is_favorite") val is_favorite : Boolean,
    @SerializedName("created_at") val created_at : String
)