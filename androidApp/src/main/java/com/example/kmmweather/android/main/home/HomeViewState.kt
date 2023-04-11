package com.example.kmmweather.android.main.home

import com.example.kmmweather.android.core.ViewState
import com.example.kmmweather.entities.Forecast as UiForecast

sealed class HomeViewState: ViewState {

    object NoData : HomeViewState()

    class Error(val cause: String) : HomeViewState()

    class Forecast(val forecast: UiForecast) : HomeViewState()

}