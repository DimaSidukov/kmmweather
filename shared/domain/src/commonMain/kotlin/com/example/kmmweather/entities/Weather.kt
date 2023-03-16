package com.example.kmmweather.entities

interface Weather {

    val elevation: Double

    val generationTimeMs: Double

    val hourly: Hourly

    val hourlyUnits: HourlyUnits

    val latitude: Double

    val longitude: Double

    val timezone: String

    val timezoneAbbreviation: String

    val utcOffsetSeconds: Int

}