package com.example.data.body

import com.example.domain.entities.HourlyUnits
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class HourlyUnitsBody(
    @SerialName("temperature_2m")
    override val temperature: String,
    override val time: String,
    @SerialName("weathercode")
    override val weatherCode: String
) : HourlyUnits