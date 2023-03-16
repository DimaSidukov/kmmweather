package com.example.kmmweather.sources.remote

import com.example.kmmweather.entities.Weather

interface WeatherSource {

    suspend fun getWeatherForToday(latitude: Double, longitude: Double): Weather

}