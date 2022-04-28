package com.example.agricult.models.addAds

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddAds(
    val price: String,
//    val description: String? = null,
    val address: String,
    val phone: String,
    val categoryId: Int,
    val userId: Int,
    val moderationStatusId: Int = 1,
    val title: String,
//    val media: Bitmap? = null
): Parcelable
