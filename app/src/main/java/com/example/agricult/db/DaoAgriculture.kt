package com.example.agricult.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.agricult.models.loginResult.Data

@Dao
interface DaoAgriculture {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToken(data: Data)

    @Query("select * from token")
    fun getTokens(): LiveData<List<Data>>

    @Query("delete from token")
    fun deleteToken()

}