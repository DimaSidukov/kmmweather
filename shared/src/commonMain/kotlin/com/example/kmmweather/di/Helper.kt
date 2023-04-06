package com.example.kmmweather.di

import com.example.kmmweather.repositories.WeatherRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import kotlin.math.roundToInt

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}

class InjectionHelper : KoinComponent {

    val weatherRepository: WeatherRepository by inject()

}

//tmp
fun maxTemperature(list: List<Double>) = list.max()
fun minTemperature(list: List<Double>) = list.min()
fun doublesToInts(list: List<Double>) = list.map { it.roundToInt() }