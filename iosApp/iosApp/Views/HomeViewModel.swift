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
        
        let weatherRepository = InjectionHelper().weatherRepository
        
        @Published var currentCityForecast = ForecastBody(
            latitude: 0,
            longitude: 0,
            address: "",
            dateTemperatureRange: "",
            currentHourTemperature: 0,
            weatherDescription: "",
            dayTemperatureString: ""
        )
        
        lazy var vmCollector: Observer = {
            let collector = Observer { value in
                if let value = value as? ForecastBody {
                    let data = value
                    self.currentCityForecast = data
                }
            }
            return collector
        }()
        
        func requestLocation(lat: Double, lon: Double) {
            DispatchQueue.main.async {
                self.weatherRepository.getForecastForToday(latitude: lat, longitude: lon, completionHandler: { data, error in
                    data?.collect(collector: self.vmCollector, completionHandler: { err in
                    })
                })
            }
        }
    }
}
