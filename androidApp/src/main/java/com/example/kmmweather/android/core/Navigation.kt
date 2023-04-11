package com.example.kmmweather.android.core

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kmmweather.android.R
import com.example.kmmweather.android.main.home.HomeScreen
import com.example.kmmweather.android.main.location.LocationScreen
import com.example.kmmweather.android.main.search.SearchScreen
import com.example.kmmweather.android.main.settings.SettingsScreen

sealed class BottomNavigationItem(val label: String, val icon: Int, val screenRoute: String) {

    object Home : BottomNavigationItem("Home", R.drawable.ic_home, "home/{lat}/{lon}/{force}")
    object Search : BottomNavigationItem("Search", R.drawable.ic_search, "search")
    object Location : BottomNavigationItem("Location", R.drawable.ic_compass, "location")
    object Settings : BottomNavigationItem("Settings", R.drawable.ic_settings, "Settings")

}

@Composable
fun NavigationGraph(navController: NavHostController, scaffoldState: ScaffoldState) {
    NavHost(navController, startDestination = BottomNavigationItem.Home.screenRoute) {
        composable(BottomNavigationItem.Home.screenRoute, arguments = listOf(
            navArgument("lat") { type = NavType.FloatType },
            navArgument("lon") { type = NavType.FloatType },
            navArgument("force") {type = NavType.BoolType }
        )) {
            val lat = it.arguments?.getFloat("lat", 56.63333f)?.toDouble() ?: 56.63333
            val lon = it.arguments?.getFloat("lon", 47.86667f)?.toDouble() ?: 47.866669
            val force = it.arguments?.getBoolean("force", false) ?: false
            HomeScreen(getViewModel(), scaffoldState, lat, lon, force)
        }
        composable(BottomNavigationItem.Search.screenRoute) {
            SearchScreen(getViewModel(), navController)
        }
        composable(BottomNavigationItem.Location.screenRoute) {
            LocationScreen()
        }
        composable(BottomNavigationItem.Settings.screenRoute) {
            SettingsScreen()
        }
    }
}