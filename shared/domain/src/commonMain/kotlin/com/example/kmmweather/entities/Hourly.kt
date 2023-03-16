package com.example.kmmweather.entities

interface Hourly {

    val temperatureList: List<Double>

    val time: List<String>

    val weatherCodeList: List<Int>

}