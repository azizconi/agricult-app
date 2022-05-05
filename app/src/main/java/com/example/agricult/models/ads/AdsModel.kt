package com.example.agricult.models.ads

import com.google.gson.annotations.SerializedName

data class AdsModel (

	@SerializedName("advertisement") val advertisement : Advertisement,
	@SerializedName("likes") val likes : List<Likes>
)