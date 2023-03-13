package com.example.data.body

import com.example.domain.entities.Hourly
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyBody(
    @SerialName("temperature_2m")
    override val temperatureList: List<Double>,
    override val time: List<String>,
    @SerialName("weatherCode")
    override val weatherCodeList: List<Int>
) : Hourly