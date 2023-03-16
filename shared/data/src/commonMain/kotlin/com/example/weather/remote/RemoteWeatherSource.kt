package com.example.weather.remote

import com.example.kmmweather.sources.remote.WeatherSource
import com.example.weather.api.WeatherApi

// design https://www.figma.com/community/file/1173567673670617907
// use koin https://insert-koin.io/docs/reference/koin-mp/kmp/
// add database to show the user last saved data, while new data is being fetched
// store selected city in shared settings and request data for it
// use the architecture described here: https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-3-3-959a2628526d
class RemoteWeatherSource(private val weatherApi: WeatherApi) : WeatherSource {

    override suspend fun getWeatherForToday(latitude: Double, longitude: Double) =
        weatherApi.forecastForToday(latitude, longitude)
}