package com.example.agricult.models.ads

import com.google.gson.annotations.SerializedName


data class Moderation_status (

	@SerializedName("id") val id : Int? = null,
	@SerializedName("name") val name : String? = null,
	@SerializedName("created_at") val created_at : String? = null
)