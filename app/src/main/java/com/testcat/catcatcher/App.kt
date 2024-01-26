package com.testcat.catcatcher

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppPrefs(context : MainActivity) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("CatCatcher", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveList (key : String, list : List<Int>){
        val json = gson.toJson(list)
        sharedPreferences.edit().putString(key,json).apply()
    }
    fun getList(key : String) : List<Int>{
        val json = sharedPreferences.getString(key, "")
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

}