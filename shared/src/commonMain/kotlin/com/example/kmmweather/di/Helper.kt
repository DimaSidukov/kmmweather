package com.example.kmmweather.di

import com.example.kmmweather.repositories.WeatherRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}

class InjectionHelper : KoinComponent {

    val weatherRepository: WeatherRepository by inject()

}