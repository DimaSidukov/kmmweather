package com.example.weather.local


import com.example.kmmweather.entities.Forecast
import com.example.kmmweather.sources.local.Database
import com.example.weather.body.ForecastBody
import com.example.weather.cache.AppDatabase

class LocalWeatherSource(databaseDriverFactory: DatabaseDriverFactory) : Database {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    override suspend fun insertForecast(forecast: Forecast) {
        dbQuery.insertForecast(
            forecast.latitude,
            forecast.longitude,
            forecast.address,
            forecast.dateTemperatureRange,
            forecast.currentHourTemperature,
            forecast.weatherDescription,
            forecast.dayTemperatureString
        )
    }

    override suspend fun getForecast(latitude: Double, longitude: Double): Forecast {
        val forecast = dbQuery.selectForecastByLocation(latitude, longitude).executeAsOne()
        return ForecastBody(
            forecast.latitude,
            forecast.longitude,
            forecast.address,
            forecast.dateTemperatureRange,
            forecast.currentHourTemperature,
            forecast.weatherDescription,
            forecast.dayTemperatureString
        )
    }
}