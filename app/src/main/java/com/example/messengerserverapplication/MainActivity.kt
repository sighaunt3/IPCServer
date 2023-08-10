package com.example.messengerserverapplication

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.messengerserverapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.testtt.text = "Time Received"
        val intent = Intent( this, IPCServerService::class.java)
        println("test1")
    }

    override fun onStart() {
        println("test2")
        super.onStart()
        val client = RecentClient.client
        RecentClient.clientLiveData.observe(this, Observer { client ->
            binding.txtPackageName.text = client?.clientPackageName
            binding.txtServerPid.text = client?.processid
            binding.txtData.text = client?.data
            binding.txtIpcMethod.text = client?.ipcMethod
            binding.editTime.text = client?.time

        })
        binding.connectionStatus.text =
            if (client == null) {
                binding.linearLayoutClientState.visibility = View.INVISIBLE
                getString(R.string.no_connected_client)
            } else {
                binding.linearLayoutClientState.visibility = View.VISIBLE
                getString(R.string.last_connected_client_info)
            }

    }
    override fun onResume(){
        super.onResume()
        val client = RecentClient.client
        RecentClient.clientLiveData.observe(this, Observer { client ->
            binding.txtData.text = client?.data
            binding.txtServerPid.text = client.processid
            binding.txtPackageName.text = client.clientPackageName
            binding.txtIpcMethod.text = client?.ipcMethod
            binding.editTime.text = client?.time

        })
    }

}