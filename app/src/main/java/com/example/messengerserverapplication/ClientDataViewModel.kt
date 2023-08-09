package com.example.messengerserverapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ClientDataViewModel : ViewModel() {
    val clientDataLiveData = MutableLiveData<Client>()


    fun updateClientData(client: Client) {
        clientDataLiveData.value = client
    }
}