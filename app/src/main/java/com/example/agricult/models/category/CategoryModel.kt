package com.example.agricult.models.category

import com.google.gson.annotations.SerializedName



data class CategoryModel (

	@SerializedName("data") val data : List<Data>,
	@SerializedName("links") val links : Links,
	@SerializedName("meta") val meta : Meta
)