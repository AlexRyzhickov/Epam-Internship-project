package com.epam.bet

import android.app.Application
import com.epam.bet.modules.appModule


import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin{
            androidContext(this@MainApp)
            modules(listOf(appModule))
        }
    }


    companion object {
        private var instance: MainApp? = null

        fun getInstance(): MainApp? {
            return instance
        }
    }
}