package com.example.kmmweather.di

import com.example.kmmweather.repositories.WeatherRepository
import com.example.weather.api.WeatherApi
import com.example.weather.remote.RemoteWeatherSource
import com.example.weather.local.LocalWeatherSource
import com.example.weather.local.DatabaseDriverFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import com.example.weather.local.AppSettings
import com.example.weather.local.buildDatabaseDriveFactory
import org.koin.dsl.module

val apiModule = module {
    factoryOf(::WeatherApi)
}

val remoteWeatherSourceModule = module {
    singleOf(::RemoteWeatherSource)
}

val localWeatherSourceModule = module {
    singleOf(::LocalWeatherSource)
}

val dbDriverModule = module {
    factory {
        buildDatabaseDriveFactory()
    }
}

val weatherRepository = module {
    single { WeatherRepository(get(), get()) }
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
    remoteWeatherSourceModule,
    localWeatherSourceModule,
    weatherRepository,
    httpClientModule,
    settingsModule,
    dbDriverModule
)