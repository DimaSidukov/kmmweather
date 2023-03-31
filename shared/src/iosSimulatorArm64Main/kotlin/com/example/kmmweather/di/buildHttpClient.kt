package com.example.kmmweather.di

import io.ktor.client.*

actual fun buildHttpClient() = getIosHttpClient()