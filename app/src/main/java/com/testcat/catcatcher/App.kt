package com.testcat.catcatcher

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App : Application() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("CatCatcher", Context.MODE_PRIVATE)
    }

}