package com.example.kmmweather.di

interface AppSettings {

    fun getIsFirstLaunch(): Boolean

    fun setIsFirstLaunch()

}

expect fun buildAppSettings(): AppSettings