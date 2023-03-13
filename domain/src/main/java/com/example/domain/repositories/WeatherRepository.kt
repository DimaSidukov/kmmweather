package com.example.domain.repositories

import com.example.domain.entities.Weather

interface WeatherRepository {

    suspend fun getWeatherForToday(longitude: Double, latitude: Double): Weather

}