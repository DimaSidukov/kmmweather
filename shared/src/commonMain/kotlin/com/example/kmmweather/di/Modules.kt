package com.example.kmmweather.di

import com.example.data.api.WeatherApi
import com.example.data.sources.remote.RemoteWeatherSource
import com.example.kmmweather.repositories.WeatherRepository
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val apiModule = module {
    factory { WeatherApi(get()) }
}

val weatherSourceModule = module {
    single { RemoteWeatherSource(get()) }
}

val weatherRepository = module {
    single { WeatherRepository(get()) }
}

val httpClientModule = module {
    factory {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = true
                })
            }
        }
    }
}

fun appModule() = listOf(
    apiModule,
    weatherSourceModule,
    weatherRepository,
    httpClientModule
)