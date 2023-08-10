package com.example.messengerserverapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Process
import androidx.lifecycle.MutableLiveData
import com.example.messengerserverapplication.RecentClient.client
import com.example.messengerserverapplication.RecentClient.clientLiveData
import com.example.messengerserverapplication.databinding.ActivityMainBinding

class IPCBroadcastReceiver: BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            RecentClient.client = Client(
                intent?.getStringExtra(PACKAGE_NAME),
                Process.myPid().toString(),
                intent?.getStringExtra(DATA).toString(),
                intent?.getStringExtra(ZAMAN).toString(),
                "Broadcast"
            )
            println("broadcast")
            println(intent?.getStringExtra(ZAMAN).toString())
            println(intent?.getStringExtra(PACKAGE_NAME))
            RecentClient.updateData(client!!)

        }

}