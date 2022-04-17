package com.example.agricult.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.agricult.models.loginResult.Data

@Database(entities = [Data::class], version = 1)
abstract class AgricultureDatabase: RoomDatabase() {

    abstract fun daoAgriculture(): DaoAgriculture

    companion object {
        @Volatile
        private var INSTANCE: AgricultureDatabase? = null
        fun getInstance(context: Context): AgricultureDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AgricultureDatabase::class.java,
                        "note_data_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }

}