package com.example.kmmweather.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmmweather.entities.Weather
import com.example.kmmweather.repositories.WeatherRepository
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val repo: WeatherRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var serverResult by remember {
                        mutableStateOf<Weather?>(null)
                    }
                    LaunchedEffect(true) {
                        serverResult = repo.getForecastForToday(40.0, 32.3)
                    }
                    serverResult?.let {
                        Text(
                            text = it.hourly.time.toString(),
                            modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 30.dp),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}
