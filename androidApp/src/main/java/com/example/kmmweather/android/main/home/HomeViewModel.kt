package com.example.kmmweather.android.main.home

import android.location.Geocoder
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmmweather.repositories.WeatherRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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
            val forecast = repository.getForecastForToday(latitude!!, longitude!!)
            forecast.collect {
                it?.let { f -> state.emit(HomeViewState.Forecast(f)) }
            }
        }
    }

    private fun isNetworkConnected(connectivityManager: ConnectivityManager): Boolean {
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

}