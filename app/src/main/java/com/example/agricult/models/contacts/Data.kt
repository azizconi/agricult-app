package com.example.agricult.models.contacts

import com.google.gson.annotations.SerializedName

data class Data (

	@SerializedName("id") val id : Int,
	@SerializedName("phone") val phone : String,
	@SerializedName("email") val email : String,
	@SerializedName("created_at") val created_at : String
)