package com.example.agricult.models.loginResult

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "token")
data class Data (

	@ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int? = null,
	@SerializedName("tokenType") val tokenType : String? = null,

	@SerializedName("accessToken") val accessToken : String? = null,
	@ColumnInfo(name = "isSuccessLoading") var isSuccessLoading: Boolean = false
//	@SerializedName("user") val user : com.example.agricult.models.category.com.example.agricult.models.search.User
)