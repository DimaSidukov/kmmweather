package com.example.kmmweather.android.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmmweather.repositories.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    var state = MutableStateFlow<HomeViewState>(HomeViewState.NoData)
        private set

    fun requestData(
        latitude: Double,
        longitude: Double,
        forceRemote: Boolean,
    ) {
        viewModelScope.launch {
            val forecast = repository.getForecastForToday(latitude, longitude, forceRemote)
            forecast.collect {
                state.emit(
                    if (it.data == null) HomeViewState.Error(it.error!!.cause)
                    else HomeViewState.Forecast(it.data!!)
                )
            }
        }
    }

    fun requestLocationList() = viewModelScope.launch {
        repository.getForecastList().let {
            state.emit(HomeViewState.ForecastList(it))
        }
    }

}