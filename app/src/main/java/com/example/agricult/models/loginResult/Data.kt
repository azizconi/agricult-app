package com.example.agricult.models.loginResult

import com.google.gson.annotations.SerializedName


data class Data(

	@SerializedName("tokenType") val tokenType : String? = null,

	@SerializedName("accessToken") val accessToken : String? = null,
	var isSuccessLoading: Boolean = false
//	@SerializedName("user") val user : com.example.agricult.models.category.com.example.agricult.models.search.User
)