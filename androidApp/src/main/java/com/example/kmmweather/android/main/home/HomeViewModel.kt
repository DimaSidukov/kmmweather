package com.example.kmmweather.android.main.home

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

    fun requestData(
        latitude: Double? = null,
        longitude: Double? = null
    ) {
        viewModelScope.launch {
            val forecast = repository.getForecastForToday(latitude!!, longitude!!)
            forecast.collect {
                state.emit(
                    if (it.data == null) HomeViewState.Error(it.error!!.cause)
                    else HomeViewState.Forecast(it.data!!)
                )
            }
        }
    }

}