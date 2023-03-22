package com.example.kmmweather.android.main.home

sealed class HomeViewState {

    object NoData: HomeViewState()

    object NoInternetConnection: HomeViewState()

    object RequestLocation: HomeViewState()

    // list of something
    object Forecast: HomeViewState()

}