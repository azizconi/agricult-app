package com.example.agricult.models.contacts

import com.google.gson.annotations.SerializedName


data class ContactModel (

	@SerializedName("data") val data : List<Data>,
	@SerializedName("links") val links : Links,
	@SerializedName("meta") val meta : Meta
)