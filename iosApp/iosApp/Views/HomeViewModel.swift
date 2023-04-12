//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Android on 06.04.2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import Foundation
import shared

typealias SharedResult = Result

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
        @Published var locationList = [Forecast]()
        @Published var errorMessage: String? = nil
        
        lazy var vmCollector: Observer = {
            let collector = Observer { value in
                if let value = value as? SharedResult<ForecastBody> {
                    let data = value
                    self.currentCityForecast = data.data ?? self.currentCityForecast
                    self.errorMessage = data.error?.cause
                }
            }
            return collector
        }()
        
        func requestLocation(lat: Double, lon: Double, forceRemote: Bool) {
            DispatchQueue.main.async {
                self.weatherRepository.getForecastForToday(latitude: lat, longitude: lon, forceRemote: forceRemote, completionHandler: { data, error in
                    data?.collect(collector: self.vmCollector, completionHandler: { err in
                        if (err != nil) {
                            self.errorMessage = err?.localizedDescription
                        }
                    })
                })
            }
        }
        
        func requestLocationList() {
            DispatchQueue.main.async {
                self.weatherRepository.getForecastList(completionHandler: { data, error in
                    if (data != nil) {
                        self.locationList = data!
                    }
                })
            }
        }
    }
}
