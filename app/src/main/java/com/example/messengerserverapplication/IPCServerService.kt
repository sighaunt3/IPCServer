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

class IPCServerService : Service() {
    private val mMessenger = Messenger(IncomingHandler())
    private var ccount = 0
    // Messenger IPC - Message Handler
    internal inner class IncomingHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val receivedBundle = msg.data
            RecentClient.client = Client(
                receivedBundle.getString(PACKAGE_NAME),
                receivedBundle.getString(DATA),
                "Messenger"
            )
            RecentClient.updateData(client!!)
            println(RecentClient.clientLiveData.value?.clientData)
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

    override fun onBind(intent: Intent?): IBinder? {
        // Choose which binder we need to return based on the type of IPC the client makes
        return when (intent?.action) {
            "messengerexample" -> mMessenger.binder


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