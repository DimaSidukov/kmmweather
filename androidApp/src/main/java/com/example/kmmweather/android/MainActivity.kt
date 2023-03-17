package com.example.kmmweather.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.kmmweather.android.main.MainScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyApplicationTheme {
                rememberSystemUiController().apply {
                    setStatusBarColor(Color.Transparent)
                    setNavigationBarColor(lightPurple)
                }
                MainScreen()
            }
        }
    }
}
