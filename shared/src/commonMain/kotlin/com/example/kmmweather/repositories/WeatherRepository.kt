package com.example.kmmweather.repositories

import com.example.domain.entities.Weather
import com.example.domain.sources.remote.WeatherSource

class WeatherRepository(
    private val remoteSource: WeatherSource
) {

    suspend fun getForecastForToday(latitude: Double, longitude: Double): Weather {
        return remoteSource.getWeatherForToday(latitude, longitude)
    }

}