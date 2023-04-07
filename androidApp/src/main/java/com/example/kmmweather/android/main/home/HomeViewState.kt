package com.example.kmmweather.android.main.home

import com.example.kmmweather.entities.Forecast as UiForecast

sealed class HomeViewState {

    object NoData : HomeViewState()

    object NoInternetConnection : HomeViewState()

    object RequestLocation : HomeViewState()

    class Forecast(val forecast: UiForecast) : HomeViewState()

}