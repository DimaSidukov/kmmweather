package com.example.weather.api

import com.example.weather.body.WeatherBody
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import kotlin.coroutines.cancellation.CancellationException

class WeatherApi(private val httpClient: HttpClient) {

    companion object {
        private const val BASE_URL = "api.open-meteo.com/v1/forecast"
    }

    suspend fun forecastForToday(latitude: Double, longitude: Double): WeatherBody =
        httpClient.get(
            "https://$BASE_URL?latitude=$latitude&longitude=$longitude&hourly=temperature_2m,weathercode&forecast_days=1"
        ).body()

}