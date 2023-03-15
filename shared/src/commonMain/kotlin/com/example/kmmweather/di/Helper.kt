package com.example.kmmweather.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}