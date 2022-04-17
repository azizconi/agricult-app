package com.example.agricult.models.category

import com.example.agricult.models.category.Links
import com.google.gson.annotations.SerializedName


data class Meta (

    @SerializedName("current_page") val current_page : Int,
    @SerializedName("from") val from : Int,
    @SerializedName("last_page") val last_page : Int,
    @SerializedName("links") val links : List<Links>,
    @SerializedName("path") val path : String,
    @SerializedName("per_page") val per_page : Int,
    @SerializedName("to") val to : Int,
    @SerializedName("total") val total : Int
)