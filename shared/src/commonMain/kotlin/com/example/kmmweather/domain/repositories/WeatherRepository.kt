package com.example.kmmweather.domain.repositories

import com.example.kmmweather.domain.entities.Weather

interface WeatherRepository {

    suspend fun getWeatherForToday(longitude: Double, latitude: Double): Weather

}