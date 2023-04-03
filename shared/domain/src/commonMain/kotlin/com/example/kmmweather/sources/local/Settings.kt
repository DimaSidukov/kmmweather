package com.example.kmmweather.sources.local


interface Settings {

    fun getIsFirstLaunch(): Boolean

    fun setIsFirstLaunch(isFirstLaunch: Boolean)

}