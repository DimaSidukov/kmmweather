package com.example.kmmweather.di

import com.example.kmmweather.repositories.WeatherRepository
import com.example.weather.api.WeatherApi
import com.example.weather.remote.RemoteWeatherSource
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
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

fun appModule() = listOf(
    apiModule,
    weatherSourceModule,
    weatherRepository,
    httpClientModule
)