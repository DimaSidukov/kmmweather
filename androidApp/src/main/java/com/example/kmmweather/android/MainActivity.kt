package com.example.kmmweather.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.data.api.WeatherApi
import com.example.data.body.WeatherBody
import com.example.data.sources.remote.RemoteWeatherSourceImpl
import com.example.kmmweather.Greeting
import com.example.kmmweather.repositories.WeatherRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val serverResult by remember {
                        mutableStateOf<List<WeatherBody>>(emptyList())
                    }

                    Text(text =)
                }
            }
        }
    }
}
