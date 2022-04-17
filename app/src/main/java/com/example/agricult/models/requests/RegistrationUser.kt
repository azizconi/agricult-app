package com.example.agricult.models.requests

data class RegistrationUser(
    val name: String? = null,
    val surname: String? = null,
    val phone: String? = null,
    val date_of_birth: String? = null,
    val address: String? = null,
    val media: String? = null,
    val password: String? = null,
    val confirm_password: String? = null,
    val email: String? = null
)