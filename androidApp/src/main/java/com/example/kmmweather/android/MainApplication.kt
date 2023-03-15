package com.example.kmmweather.android

import android.app.Application
import com.example.kmmweather.di.initKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

}