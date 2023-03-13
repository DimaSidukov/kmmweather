package com.example.data.repositories

import com.example.data.body.WeatherBody
import com.example.domain.repositories.WeatherRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

// design https://www.figma.com/community/file/1173567673670617907
// use koin https://insert-koin.io/docs/reference/koin-mp/kmp/
// add database to show the user last saved data, while new data is being fetched
// store selected city in shared settings and request data for it
// use the architecture described here: https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-3-3-959a2628526d
class WeatherRepositoryImpl(private val httpClient: HttpClient) : WeatherRepository {

    companion object {
        private const val BASE_URL = "api.open-meteo.com/v1/forecast"
    }

    override suspend fun getWeatherForToday(longitude: Double, latitude: Double): WeatherBody = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            parameters.append("latitude", latitude.toString())
            parameters.append("longitude", longitude.toString())
            parameters.append("hourly", "temperature_2m, weathercode")
        }
    }.body()

}