package com.example.messengerserverapplication

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.example.messengerserverapplication.RecentClient.client
import javax.xml.xpath.XPathConstants.STRING
import android.os.Process
import android.util.Log

class IPCServerService : Service() {

    private val mMessenger = Messenger(IncomingHandler())
    private var ccount = 0
     val NOT_SENT = "Not sent!"

    // Messenger IPC - Message Handler

    internal inner class IncomingHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val receivedBundle = msg.data
            RecentClient.client = Client(
                receivedBundle.getString(PACKAGE_NAME),
                Process.myPid().toString() ,
                receivedBundle.getString(DATA).toString(),
                "Messenger"
            )
            RecentClient.updateData(client!!)
            ccount += 1
            // Send message to the client. The message contains server info
            val message = Message.obtain(this@IncomingHandler, 0)
            val bundle = Bundle()
            val pid = Process.myPid()

            bundle.putInt(CONNECTION_COUNT, ccount)
            bundle.putInt(PID, pid)

            message.data = bundle
            // The service can save the msg.replyTo object as a local variable
            // so that it can send a message to the client at any time
            msg.replyTo.send(message)
        }
    }

    private val clientDataViewModel: ClientDataViewModel
        get() = (application as MyApplication).clientDataViewModel

        private val aidlBinder = object : IIPCExample.Stub() {

        override fun getPid(): Int = Process.myPid()

        override fun getConnectionCount(): Int = IPCServerService.connectionCount

        override fun postVal(packageName: String?, pid: Int, data : String) {
            Companion.connectionCount++
            // Get message from client. Save recent connected client info.
            RecentClient.client = Client(
                packageName ?: NOT_SENT,
                pid.toString(),
                data,
                "AIDL"
            )
            RecentClient.updateData(client!!)

            clientDataViewModel.clientDataLiveData.postValue(RecentClient.client)
            //myApplication.clientDataViewModel.updateClientData(RecentClient.client!!)
            Log.d("INCC", "GELDİ")
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        println("hello")
        // Choose which binder we need to return based on the type of IPC the client makes
        return when (intent?.action) {
            "messengerexample" -> mMessenger.binder
            "aidlexample" -> aidlBinder

            else -> null
        }
    }
    override fun onUnbind(intent: Intent?): Boolean {
        RecentClient.client = null
        return super.onUnbind(intent)
    }


    companion object {
        var connectionCount: Int = 0
    }
}