package com.example.agricult.models.search

import com.example.agricult.models.search.Data
import com.example.agricult.models.search.Links
import com.google.gson.annotations.SerializedName


data class Result (

    @SerializedName("data") val data : List<Data>,
    @SerializedName("links") val links : Links
)