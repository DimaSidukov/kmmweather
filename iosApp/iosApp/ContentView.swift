import SwiftUI
import shared
import SVGView

struct RedView: View {
    var body: some View {
        Color.red
    }
}
struct BlueView: View {
    var body: some View {
        Color.blue
    }
}


struct ContentView: View {
    
    @State private var tabSelection = 1
    @State private var latitude = 56.633331
    @State private var longitude = 47.866669
    @State private var forceRemote = false
    
    init() {
        UITabBar.appearance().backgroundColor = UIColor(
            red: 127 / 255,
            green: 76 / 255,
            blue: 210 / 255,
            alpha: 1
        )
        UITabBar.appearance().unselectedItemTintColor = UIColor(
            red: 1,
            green: 1,
            blue: 1,
            alpha: 0.7
        )
    }
    
    var body: some View {
        TabView(selection: $tabSelection) {
            NavigationView {
                HomeView(lat: latitude, lon: longitude, forceRemote: forceRemote)
            }.tabItem {
                    Image(systemName: "house.fill")
                }
            .tag(1)
            NavigationView {
                SearchView(tabSelection: $tabSelection, latitude: $latitude, longitude: $longitude, forceRemote: $forceRemote)
            }.tabItem {
                Image(systemName: "magnifyingglass")
            }
            .tag(2)
            LocationView()
                .tabItem {
                    Image(systemName: "safari")
                }
                .tag(3)
            SettingsView()
                .tabItem {
                    Image(systemName: "gearshape")
                }
                .tag(4)
        }
        .accentColor(.white)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

