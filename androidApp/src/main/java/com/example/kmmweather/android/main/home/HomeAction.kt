package com.example.kmmweather.android.main.home

sealed class HomeAction {

    class FetchForecast(latitude: Double, longitude: Double) : HomeAction()

}
