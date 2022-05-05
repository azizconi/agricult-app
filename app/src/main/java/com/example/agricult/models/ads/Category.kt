package com.example.agricult.models.ads

import com.google.gson.annotations.SerializedName

data class Category (

	@SerializedName("id") val id : Int? = null,
	@SerializedName("title") val title : String? = null,
	@SerializedName("icon") val icon : String? = null,
	@SerializedName("quantity") val quantity : Int? = null,
	@SerializedName("created_at") val created_at : String? = null
)