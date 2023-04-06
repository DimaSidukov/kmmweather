//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Android on 06.04.2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import Foundation
import shared

extension HomeView {
    @MainActor class HomeViewModel: ObservableObject {
        
        @Published var currentCityForecast = Forecast(
            address: "",
            dateTemperatureRange: "",
            currentHourTemperature: 0,
            weatherDescription: "",
            dayTemperatureList: [KotlinInt(0)]
        )
        
        func requestLocation(lat: Double, lon: Double) {
            InjectionHelper().weatherRepository.getForecastForToday(latitude: lat, longitude: lon) { weather, error in
                DispatchQueue.main.async {
                    let hour = Calendar.current.component(.hour, from: Date())
                    let tmpList = weather!.hourly.temperatureList
                    self.currentCityForecast = Forecast(
                        address: "Yoshkar-Ola",
                        dateTemperatureRange: "TEST \(HelperKt.maxTemperature(list: tmpList)) \(HelperKt.minTemperature(list: tmpList))",
                        currentHourTemperature: tmpList[hour].int32Value,
                        weatherDescription: weather!.hourlyUnits.weatherCode,
                        dayTemperatureList: HelperKt.doublesToInts(list: tmpList)
                    )
                }
            }
        }
    }
}
