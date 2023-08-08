package com.example.messengerserverapplication

import androidx.lifecycle.MutableLiveData


data class Client(
    var clientPackageName: String?,
    var clientData: String?,
    var ipcMethod: String
)

object RecentClient {
    var client: Client? = null
    val clientLiveData = MutableLiveData<Client>()
    fun updateData(client: Client){
        clientLiveData.postValue(client)
    }

}