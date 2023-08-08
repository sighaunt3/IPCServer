package com.example.messengerserverapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.messengerserverapplication.RecentClient.client
import com.example.messengerserverapplication.RecentClient.clientLiveData
import com.example.messengerserverapplication.databinding.ActivityMainBinding

class IPCBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        RecentClient.client = Client(
            intent?.getStringExtra(PACKAGE_NAME),
            intent?.getStringExtra(DATA),
            "Broadcast"
        )
        RecentClient.updateData(client!!)
        println("test")
        println(clientLiveData.value?.clientData)

    }

}