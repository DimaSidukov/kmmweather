package com.example.weather.api

import com.example.weather.body.WeatherBody
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class WeatherApi(private val httpClient: HttpClient) {

    companion object {
        private const val BASE_URL = "api.open-meteo.com/v1/forecast"
    }

    suspend fun forecastForToday(latitude: Double, longitude: Double): WeatherBody = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            parameters.append("latitude", latitude.toString())
            parameters.append("longitude", longitude.toString())
            parameters.append("hourly", "temperature_2m, weathercode")
        }
    }.body()

}