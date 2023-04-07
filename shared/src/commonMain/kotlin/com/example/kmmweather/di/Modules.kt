package com.example.kmmweather.di

import com.example.kmmweather.repositories.WeatherRepository
import com.example.weather.api.WeatherApi
import com.example.weather.remote.RemoteWeatherSource
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import com.example.weather.local.AppSettings
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val apiModule = module {
    factoryOf(::WeatherApi)
}

val weatherSourceModule = module {
    singleOf(::RemoteWeatherSource)
}

val weatherRepository = module {
    single { WeatherRepository(get()) }
}

val httpClientModule = module {
    factory {
        buildHttpClient()
    }
}

val settingsModule = module {
    singleOf(::AppSettings)
}

fun appModule() = listOf(
    apiModule,
    weatherSourceModule,
    weatherRepository,
    httpClientModule,
    settingsModule
)