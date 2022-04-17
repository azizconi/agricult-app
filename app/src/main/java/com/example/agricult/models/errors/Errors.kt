package com.example.agricult.models.errors

import com.google.gson.annotations.SerializedName

data class Errors(@SerializedName("phone") val phone: List<String>? = null)
