package com.example.agricult.models.errors

import com.google.gson.annotations.SerializedName

data class ErrorMessageLogin(
    @SerializedName("message")val message: String? = null,
    @SerializedName("errors") val errors: Errors? = null
)
