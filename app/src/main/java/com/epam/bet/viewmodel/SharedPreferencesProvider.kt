package com.epam.bet.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesProvider(var context: Context) {
    private lateinit var sharedPreferences: SharedPreferences


    fun get(key: String, defValue: String = "none"): String? {
        if (!this::sharedPreferences.isInitialized){
            sharedPreferences = context.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        }
        return sharedPreferences.getString(key, defValue)
    }
}