package com.example.agricult.models.loginResult

import com.google.gson.annotations.SerializedName

data class User (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("surname") val surname : String,
	@SerializedName("phone") val phone : String,
	@SerializedName("email") val email : String
)