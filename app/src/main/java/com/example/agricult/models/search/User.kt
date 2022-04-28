package com.example.agricult.models.search

import com.google.gson.annotations.SerializedName


data class User (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("surname") val surname : String,
	@SerializedName("phone") val phone : String,
	@SerializedName("date_of_birth") val date_of_birth : String,
	@SerializedName("address") val address : String,
	@SerializedName("image") val image : String,
	@SerializedName("is_active") val is_active : Boolean,
	@SerializedName("is_admin") val is_admin : String,
	@SerializedName("created_at") val created_at : String
)