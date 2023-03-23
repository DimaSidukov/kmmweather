package com.example.kmmweather.di

class AndroidAppSettings: AppSettings {

    override fun getIsFirstLaunch(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setIsFirstLaunch() {
        TODO("Not yet implemented")
    }
}

actual fun buildAppSettings(): AppSettings = AndroidAppSettings()