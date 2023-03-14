package com.example.kmmweather.repositories

import com.example.domain.sources.remote.RemoteWeatherSource

class WeatherRepository(
    private val remoteSource: RemoteWeatherSource
) {

    suspend fun getForecastForToday(latitude: Double, longitude: Double) {
        remoteSource.getWeatherForToday(latitude, longitude)
    }

}