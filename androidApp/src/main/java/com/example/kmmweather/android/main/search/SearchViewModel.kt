package com.example.kmmweather.android.main.search

import androidx.lifecycle.ViewModel
import com.example.kmmweather.repositories.WeatherRepository

class SearchViewModel(private val repository: WeatherRepository): ViewModel() {

    suspend fun getCoordinates(address: String) = repository.getCoordinates(address)

}