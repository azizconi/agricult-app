package com.example.agricult

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.agricult.ui.screen.navigation.Navigation
import com.example.agricult.viewmodel.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

class MainActivity : ComponentActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()

    private val profileRequestViewModel by viewModels<ProfileRequestViewModel>()
    private val categoriesViewModel by viewModels<CategoriesViewModel>()
    private val categoryViewModel by viewModels<CategoryViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()
    private val favouriteViewModel by viewModels<FavouriteViewModel>()

    private val dataStoreViewModel by viewModels<DataStoreViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Navigation(
                loginViewModel = loginViewModel,
                profileRequestViewModel = profileRequestViewModel,
                categoriesViewModel = categoriesViewModel,
                categoryViewModel = categoryViewModel,
                searchViewModel = searchViewModel,
                dataStoreViewModel = dataStoreViewModel,
                favouriteViewModel = favouriteViewModel
            )
        }



    }


}





