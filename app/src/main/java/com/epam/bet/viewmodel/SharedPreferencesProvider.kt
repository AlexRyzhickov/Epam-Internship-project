package com.epam.bet.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.epam.bet.R
import com.epam.bet.MainApp

class SharedPreferencesProvider(var application: Application){
    private lateinit var sharedPreferences: SharedPreferences

    fun get(key: String, defValue: String = "none"): String? {
        if (!this::sharedPreferences.isInitialized){
            sharedPreferences = application.applicationContext.getSharedPreferences(MainApp.applicationContext().getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        return sharedPreferences.getString(key, defValue)
    }
}