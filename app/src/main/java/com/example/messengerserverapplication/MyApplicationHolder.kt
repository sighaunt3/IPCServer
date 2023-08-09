package com.example.messengerserverapplication

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object MyApplicationHolder {
    private var context: Context? = null
    private var viewModel: ClientDataViewModel? = null

    fun init(context: Context, viewModel: ClientDataViewModel) {
        this.context = context.applicationContext
        this.viewModel = viewModel
    }

    fun getApplicationContext(): Context? {
        return context
    }

    fun getViewModel(): ClientDataViewModel? {
        return viewModel
    }
}