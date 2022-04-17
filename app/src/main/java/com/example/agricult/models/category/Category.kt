package com.example.agricult.models.category

import com.google.gson.annotations.SerializedName

data class Category (

	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("icon") val icon : String,
	@SerializedName("quantity") val quantity : Int,
	@SerializedName("created_at") val created_at : String
)