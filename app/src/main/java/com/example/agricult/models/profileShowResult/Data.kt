package com.example.agricult.models.profileShowResult

import com.google.gson.annotations.SerializedName

data class Data (

	@SerializedName("id") val id : Int? = null,
	@SerializedName("name") val name : String? = null,
	@SerializedName("surname") val surname : String? = null,
	@SerializedName("phone") val phone : String? = null,
	@SerializedName("date_of_birth") val date_of_birth : String? = null,
	@SerializedName("address") val address : String? = null,
	@SerializedName("image") val image : String? = null,
	@SerializedName("is_active") val is_active : Boolean? = null,
	@SerializedName("is_admin") val is_admin : String? = null,
	@SerializedName("created_at") val created_at : String? = null
)