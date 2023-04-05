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
        TabView {
            HomeView()
                .tabItem {
                    Image(systemName: "house.fill")
                }
            SearchView()
                .tabItem {
                    Image(systemName: "magnifyingglass")
                }
            LocationView()
                .tabItem {
                    Image(systemName: "safari")
                }
            SettingsView()
                .tabItem {
                    Image(systemName: "gearshape")
                }
        }
        .accentColor(.white)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

