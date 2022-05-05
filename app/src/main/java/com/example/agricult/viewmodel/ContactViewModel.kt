package com.example.agricult.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.agricult.models.contacts.ContactModel
import com.example.agricult.models.contacts.Data
import com.example.agricult.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactViewModel(application: Application): AndroidViewModel(application) {


    private var setContactModel: List<Data> = emptyList()
    var getContactAnnouncementModel = mutableStateOf(setContactModel)


    fun getContacts(token: String, dataStoreViewModel: DataStoreViewModel) {
        RetrofitInstance().api().getContact(token = "Bearer $token")
            .enqueue(object : Callback<ContactModel> {
                override fun onResponse(
                    call: Call<ContactModel>,
                    response: Response<ContactModel>
                ) {
                    if (response.code() == 401) {
                        dataStoreViewModel.clearDataStore()
                    }

                    if (response.isSuccessful) {

                        response.body().let {
                            if (it != null) {
                                getContactAnnouncementModel.value = it.data
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ContactModel>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}", )
                }

            })

    }

}