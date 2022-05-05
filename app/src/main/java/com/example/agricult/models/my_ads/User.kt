package com.example.agricult.models.my_ads

data class User(
    val address: String,
    val created_at: String,
    val date_of_birth: String,
    val id: Int,
    val image: String,
    val is_active: Boolean,
    val is_admin: Any,
    val name: String,
    val phone: String,
    val surname: String
)