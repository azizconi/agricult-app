package com.example.agricult.models.search

import com.google.gson.annotations.SerializedName

data class Moderation_status (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("created_at") val created_at : String
)