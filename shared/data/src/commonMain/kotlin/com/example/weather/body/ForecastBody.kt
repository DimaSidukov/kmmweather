package com.example.weather.body

import com.example.kmmweather.entities.Forecast

data class ForecastBody(
    override val latitude: Double,
    override val longitude: Double,
    override var address: String,
    override val dateTemperatureRange: String,
    override val currentHourTemperature: Int,
    override val weatherDescription: String,
    override val dayTemperatureString: String,
) : Forecast {
    override val dayTemperatureList: List<Int> = dayTemperatureString.split(",").map{ it.toInt() }
}