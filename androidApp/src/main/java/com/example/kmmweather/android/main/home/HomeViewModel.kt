package com.example.kmmweather.android.main.home

import android.location.Geocoder
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmmweather.repositories.WeatherRepository
import com.example.kmmweather.ui.Forecast
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HomeViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    val userAction = Channel<HomeAction>(Channel.UNLIMITED)
    var state = MutableStateFlow<HomeViewState>(HomeViewState.NoData)
        private set

    // ask user for location on first launch
    // if location not granted, go idle
    // if internet not present - load from db and show snackbar
    fun requestData(
        connectivityManager: ConnectivityManager,
        geocoder: Geocoder,
        latitude: Double? = null,
        longitude: Double? = null
    ) {
        viewModelScope.launch {
            val data = repository.getForecastForToday(latitude!!, longitude!!)
            val address = geocoder
                .getFromLocation(latitude, longitude, 1)
                ?.get(0)
                ?.getAddressLine(0) ?: ""
            val date = SimpleDateFormat("d MMM yyyy EEE", Locale.getDefault())
                .format(Calendar.getInstance().time)
            val currentHour = Calendar
                .getInstance()
                .get(Calendar.HOUR_OF_DAY)
            state.emit(
                HomeViewState.Forecast(
                    Forecast(
                        address = address,
                        dateTemperatureRange = "$date " +
                                "${data.hourly.temperatureList.min().roundToInt()}°C/" +
                                "${data.hourly.temperatureList.max().roundToInt()}°C",
                        currentHourTemperature = data.hourly.temperatureList[currentHour].roundToInt(),
                        weatherDescription = data.hourly.weatherCodeList[currentHour].wmoToString(),
                        dayTemperatureList = data.hourly.temperatureList.map { t -> t.roundToInt() }
                    )
                )
            )
        }
    }

    private fun isNetworkConnected(connectivityManager: ConnectivityManager): Boolean {
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
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