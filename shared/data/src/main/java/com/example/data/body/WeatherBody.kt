package com.example.data.body

import com.example.domain.entities.Hourly
import com.example.domain.entities.HourlyUnits
import com.example.domain.entities.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherBody(
    override val elevation: Double,
    @SerialName("generationtime_ms")
    override val generationTimeMs: Double,
    override val hourly: Hourly,
    @SerialName("hourly_units")
    override val hourlyUnits: HourlyUnits,
    override val latitude: Double,
    override val longitude: Double,
    override val timezone: String,
    @SerialName("timezone_abbreviation")
    override val timezoneAbbreviation: String,
    @SerialName("utc_offset_seconds")
    override val utcOffsetSeconds: Int
) : Weather