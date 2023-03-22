package com.example.kmmweather.android.main.home

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.example.kmmweather.di.InjectionHelper
import com.example.kmmweather.repositories.WeatherRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {

    private val repository: WeatherRepository = InjectionHelper().weatherRepository

    val userAction = Channel<HomeAction>(Channel.UNLIMITED)
    var state = MutableStateFlow<HomeViewState>(HomeViewState.NoData)
        private set

    // ask user for location on first launch
    // if location not granted, go idle
    // if internet not present - load from db and show snackbar
    fun requestData(
        connectivityManager: ConnectivityManager,
        // first launch ?
        latitude: Double? = null,
        longitude: Double? = null
    ) {
        if (latitude == null && longitude == null) {
            state.value = HomeViewState.RequestLocation
        }
        if (!isNetworkConnected(connectivityManager)) {
            repository.getForecastForToday()
        }
    }

    private fun isNetworkConnected(connectivityManager: ConnectivityManager): Boolean {
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

}