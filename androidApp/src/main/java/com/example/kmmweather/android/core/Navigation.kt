package com.example.kmmweather.android.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kmmweather.android.R
import com.example.kmmweather.android.main.home.HomeScreen
import com.example.kmmweather.android.main.location.LocationScreen
import com.example.kmmweather.android.main.search.SearchScreen
import com.example.kmmweather.android.main.settings.SettingsScreen

sealed class BottomNavigationItem(val label: String, val icon: Int, val screenRoute: String) {

    object Home : BottomNavigationItem("Home", R.drawable.ic_home, "home")
    object Search : BottomNavigationItem("Search", R.drawable.ic_search, "search")
    object Location : BottomNavigationItem("Location", R.drawable.ic_compass, "location")
    object Settings : BottomNavigationItem("Settings", R.drawable.ic_settings, "Settings")

}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavigationItem.Home.screenRoute) {
        composable(BottomNavigationItem.Home.screenRoute) {
            HomeScreen(getViewModel())
        }
        composable(BottomNavigationItem.Search.screenRoute) {
            SearchScreen()
        }
        composable(BottomNavigationItem.Location.screenRoute) {
            LocationScreen()
        }
        composable(BottomNavigationItem.Settings.screenRoute) {
            SettingsScreen()
        }
    }
}