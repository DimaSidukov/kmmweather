//
//  SearchViewModel.swift
//  iosApp
//
//  Created by Android on 12.04.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension SearchView {
    @MainActor class SearchViewModel: ObservableObject {
        
        let weatherRepository = InjectionHelper().weatherRepository
        
        func getCoordinates(address: String, onDataFetched: ((_ lat: Double, _ lon: Double) -> Void)?) {
            DispatchQueue.main.async {
                self.weatherRepository.getCoordinates(address: address) { data, error in
                    let lat = data?.first
                    let lon = data?.second
                    if (lat != nil && lon != nil) {
                        onDataFetched?(lat!.doubleValue, lon!.doubleValue)
                    }
                }
            }
        }
        
    }
}
