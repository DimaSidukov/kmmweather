package com.example.weather.body

import com.example.kmmweather.entities.HourlyUnits
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyUnitsBody(
    @SerialName("temperature_2m")
    override val temperature: String,
    override val time: String,
    @SerialName("weathercode")
    override val weatherCode: String
) : HourlyUnits