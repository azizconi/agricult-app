package com.example.agricult.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCES_NAME = "agriculture_preferences"

class DataStoreRepository(context: Context) {

    private object PreferencesKeys {
        val auth_key = preferencesKey<String>("auth_key")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun saveToDataStore(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.auth_key] = name
        }
    }


    val accessToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.auth_key]
        }


    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}