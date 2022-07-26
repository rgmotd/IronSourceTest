package com.example.ironsourcetest

import android.app.Application
import com.example.ironsourcetest.di.appModule
import com.example.ironsourcetest.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, dataModule)
        }
    }
}