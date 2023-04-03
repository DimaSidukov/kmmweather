package com.example.kmmweather.repositories

import com.example.kmmweather.entities.Weather
import com.example.kmmweather.ui.Forecast
import com.example.weather.remote.RemoteWeatherSource

// find a way to provide with an interface and make it compatible with Koin's injection
class WeatherRepository(
    private val remoteSource: RemoteWeatherSource
) {

    suspend fun getForecastForToday(latitude: Double, longitude: Double): Weather {
        return remoteSource.getWeatherForToday(latitude, longitude)
    }

}