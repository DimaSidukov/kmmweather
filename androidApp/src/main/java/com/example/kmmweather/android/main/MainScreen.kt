package com.example.kmmweather.android.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kmmweather.android.core.BottomNavigationItem.*
import com.example.kmmweather.android.core.NavigationGraph
import com.example.kmmweather.android.lightPurple
import com.example.kmmweather.android.unselectedNavigationItemColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        NavigationGraph(navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val destinations = listOf(Home, Search, Location, Settings)

    BottomNavigation(
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.systemBars.only(
                WindowInsetsSides.Bottom
            )
        ),
        backgroundColor = lightPurple,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        destinations.forEach { destination ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.label
                    )
                },
                label = null,
                selectedContentColor = Color.White,
                unselectedContentColor = unselectedNavigationItemColor,
                alwaysShowLabel = false,
                selected = currentRoute == destination.screenRoute,
                onClick = {
                    navController.navigate(destination.screenRoute) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}