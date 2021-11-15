package com.example.myapplication1

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApplication: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var globalContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        globalContext = applicationContext
    }
}