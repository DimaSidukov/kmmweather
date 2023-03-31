import SwiftUI
import shared

struct ContentView: View {
    
    
    let daz: DomainWeather = InjectionHelper().weatherRepository.getForecastForToday(
        latitude: 32,
        longitude: 42
    ) { data, error in
        return data
    }
    
    
	var body: some View {
		Text(foundData)
	}
}
