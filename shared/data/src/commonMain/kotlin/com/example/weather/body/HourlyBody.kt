package com.example.weather.body

import com.example.kmmweather.entities.Hourly
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyBody(
    @SerialName("temperature_2m")
    override val temperatureList: List<Double>,
    @SerialName("time")
    override val time: List<String>,
    @SerialName("weathercode")
    override val weatherCodeList: List<Int>
) : Hourly