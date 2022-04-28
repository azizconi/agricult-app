package com.example.agricult.models.search

import com.google.gson.annotations.SerializedName

data class SearchModel (

	@SerializedName("result") val result : Result,
	@SerializedName("count") val count : Int
)