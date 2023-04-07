package com.example.kmmweather.repositories

import com.example.kmmweather.entities.Forecast
import com.example.kmmweather.utils.format
import com.example.weather.body.ForecastBody
import com.example.weather.local.LocalWeatherSource
import com.example.weather.remote.RemoteWeatherSource
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

// find a way to provide with an interface and make it compatible with Koin's injection
class WeatherRepository(
    private val remoteSource: RemoteWeatherSource,
    private val localSource: LocalWeatherSource
) {
    suspend fun addForecast(forecast: Forecast) {
        println(forecast)
        localSource.insertForecast(forecast)
    }

    suspend fun getForecastForToday(latitude: Double, longitude: Double, isRemote: Boolean): Forecast? {
        return if (isRemote) {
            getRemoteForecast(latitude, longitude)
        } else {
            localSource.getForecast(latitude, longitude)
        }
    }

    private suspend fun getRemoteForecast(latitude: Double, longitude: Double) : Forecast {
        val date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).format("d MMM yyyy EEE")
        val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
        val weather = remoteSource.getWeatherForToday(latitude, longitude)
        val forecast = ForecastBody(
            latitude = latitude,
            longitude = longitude,
            address = "",
            dateTemperatureRange = "$date " +
                    "${weather.hourly.temperatureList.min().roundToInt()}°C/" +
                    "${weather.hourly.temperatureList.max().roundToInt()}°C",
            currentHourTemperature = weather.hourly.temperatureList[currentHour].roundToInt(),
            weatherDescription = weather.hourly.weatherCodeList[currentHour].wmoToString(),
            dayTemperatureString = weather.hourly.temperatureList.map { t -> t.roundToInt() }.joinToString(",")
        )
//        localSource.insertForecast(forecast)
        return forecast
    }

    companion object {
        // temporary here, move out to repo
        fun Int?.wmoToString(): String = when (this) {
            0 -> "Clear sky"
            1, 2, 3 -> "Mainly clear, partly cloudy, and overcast"
            45, 48 -> "Fog and depositing rime fog"
            51, 53, 55 -> "Drizzle: Light, moderate, and dense intensity"
            56, 57 -> "Freezing Drizzle: Light and dense intensity"
            61, 63, 65 -> "Rain: Slight, moderate and heavy intensity"
            66, 67 -> "Freezing Rain: Light and heavy intensity"
            71, 73, 75 -> "Snow fall: Slight, moderate, and heavy intensity"
            77 -> "Snow grains"
            80, 81, 82 -> "Rain showers: Slight, moderate, and violent"
            85, 86 -> "Snow showers slight and heavy"
            95 -> "Thunderstorm: Slight or moderate"
            96, 99 -> "Thunderstorm with slight and heavy hail"
            else -> ""
        }
    }

}