package com.example.kmmweather.ui

data class Forecast(
    val address: String,
    val dateTemperatureRange: String,
    val currentHourTemperature: Int,
    val weatherDescription: String,
    val dayTemperatureList: List<Int>
)