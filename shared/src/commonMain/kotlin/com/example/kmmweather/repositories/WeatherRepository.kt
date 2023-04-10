package com.example.kmmweather.repositories

import com.example.kmmweather.entities.Forecast
import com.example.kmmweather.utils.format
import com.example.kmmweather.utils.wrapToAny
import com.example.weather.body.ForecastBody
import com.example.weather.local.LocalWeatherSource
import com.example.weather.remote.RemoteWeatherSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

// find a way to provide with an interface and make it compatible with Koin's injection
class WeatherRepository(
    private val remoteSource: RemoteWeatherSource,
    private val localSource: LocalWeatherSource
) {

    suspend fun getForecastForToday(latitude: Double, longitude: Double): Flow<Forecast?> =
        flow {
            tryEmit(getLocalForecast(latitude, longitude))
            tryEmit(getRemoteForecast(latitude, longitude))
        }.wrapToAny()

    private suspend fun <T> FlowCollector<T>.tryEmit(data: T) = try {
        emit(data)
    } catch (_: Throwable) {

    }

    private suspend fun getRemoteForecast(latitude: Double, longitude: Double): Forecast {
        val date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            .format("d MMM yyyy EEE")
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
            dayTemperatureString = weather.hourly.temperatureList
                .map { t -> t.roundToInt() }
                .joinToString(",")
        )
        delay(2000)
        localSource.insertForecast(forecast)
        return forecast
    }

    private suspend fun getLocalForecast(latitude: Double, longitude: Double) =
        localSource.getForecast(latitude, longitude)

    companion object {
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