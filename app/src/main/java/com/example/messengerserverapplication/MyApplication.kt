package com.example.messengerserverapplication

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class MyApplication : Application() {

    val clientDataViewModel: ClientDataViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(ClientDataViewModel::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        MyApplicationHolder.init(applicationContext, clientDataViewModel)
    }
}
