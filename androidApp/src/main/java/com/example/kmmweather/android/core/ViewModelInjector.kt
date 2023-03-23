package com.example.kmmweather.android.core

import androidx.lifecycle.ViewModel
import com.example.kmmweather.android.main.home.HomeViewModel
import com.example.kmmweather.android.main.location.LocationViewModel
import com.example.kmmweather.di.InjectionHelper

internal val injector = InjectionHelper()

internal inline fun <reified T : ViewModel> getViewModel() = when (T::class) {
    HomeViewModel::class -> HomeViewModel(injector.weatherRepository)
    else -> LocationViewModel()
} as T