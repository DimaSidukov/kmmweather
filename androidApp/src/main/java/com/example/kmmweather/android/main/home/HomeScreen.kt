package com.example.kmmweather.android.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kmmweather.android.R
import com.example.kmmweather.android.darkPurple

// guide to MVI architecture: https://blog.mindorks.com/mvi-architecture-android-tutorial-for-beginners-step-by-step-guide/


@Preview
@Composable
fun HomeScreen() {
    Box {
        HomeScreenBackground()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(
                    WindowInsets.systemBars.only(
                        WindowInsetsSides.Top + WindowInsetsSides.Bottom
                    )
                )
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp),
                onClick = {

                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_account),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp),
                onClick = {

                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_drawer),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun HomeScreenBackground() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_night_1),
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF311C7F),
                            darkPurple
                        )
                    )
                )
        )
    }
}