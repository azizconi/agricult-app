package com.example.agricult.models.category

import com.google.gson.annotations.SerializedName

data class ModerationStatus (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("created_at") val created_at : String
)