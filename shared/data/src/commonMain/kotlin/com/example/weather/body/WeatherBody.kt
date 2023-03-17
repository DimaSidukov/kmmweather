package com.example.weather.body

import com.example.kmmweather.entities.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherBody(
    override val elevation: Double,
    @SerialName("generationtime_ms")
    override val generationTimeMs: Double,
    override val hourly: HourlyBody,
    @SerialName("hourly_units")
    override val hourlyUnits: HourlyUnitsBody,
    override val latitude: Double,
    override val longitude: Double,
    override val timezone: String,
    @SerialName("timezone_abbreviation")
    override val timezoneAbbreviation: String,
    @SerialName("utc_offset_seconds")
    override val utcOffsetSeconds: Int
) : Weather