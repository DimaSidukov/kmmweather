package com.example.kmmweather.repositories

import com.example.kmmweather.entities.Forecast
import com.example.kmmweather.utils.Geocoder
import com.example.kmmweather.utils.formatCurrentTime
import com.example.kmmweather.utils.wrapToAny
import com.example.weather.body.ForecastBody
import com.example.weather.body.Result
import com.example.weather.body.WeatherBody
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

    suspend fun getForecastForToday(
        latitude: Double,
        longitude: Double,
        forceRemote: Boolean,
    ): Flow<Result<Forecast>> = flow {
        if (!forceRemote) tryEmit(getLocalForecast(latitude, longitude))
        tryEmit(getRemoteForecast(latitude, longitude))
    }.wrapToAny()

    private suspend fun <T> FlowCollector<T>.tryEmit(data: T) = try {
        emit(data)
    } catch (_: Throwable) {

    }

    private suspend fun getRemoteForecast(
        latitude: Double,
        longitude: Double
    ): Result<Forecast> {
        val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
        val weather: WeatherBody?
        try {
            weather = remoteSource.getWeatherForToday(latitude, longitude)
        } catch (e: Exception) {
            return Result(null, e.message ?: "Error requesting data!")
        }
        val forecast = ForecastBody(
            latitude = latitude,
            longitude = longitude,
            address = Geocoder.decodeLocation(latitude, longitude),
            dateTemperatureRange = "${formatCurrentTime("d MMM yyyy EEE")} " +
                    "${weather.hourly.temperatureList.min().roundToInt()}°C/" +
                    "${weather.hourly.temperatureList.max().roundToInt()}°C",
            currentHourTemperature = weather.hourly.temperatureList[currentHour].roundToInt(),
            weatherDescription = weather.hourly.weatherCodeList[currentHour].wmoToString(),
            dayTemperatureString = weather.hourly.temperatureList
                .map { t -> t.roundToInt() }
                .joinToString(",")
        )
        localSource.insertForecast(forecast)
        return Result(forecast)
    }

    private suspend fun getLocalForecast(latitude: Double, longitude: Double): Result<Forecast> {
        val data = localSource.getForecast(latitude, longitude)
        return Result(
            data = data,
            error = if (data == null) "No cached data available!" else null
        )
    }

    suspend fun getForecastList() = localSource.getForecastList()

    suspend fun getCoordinates(address: String) = Geocoder.encodeLocation(address)

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