package com.example.kmmweather.sources.local

import com.example.kmmweather.entities.Forecast

interface Database {
    suspend fun insertForecast(forecast: Forecast)
    suspend fun getForecast(latitude: Double, longitude: Double) : Forecast?
}