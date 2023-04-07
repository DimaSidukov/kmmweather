package com.example.kmmweather.android.main.home

import android.location.Geocoder
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmmweather.android.R
import com.example.kmmweather.android.darkPurple
import com.example.kmmweather.android.darkPurple2
import com.example.kmmweather.entities.Forecast
import java.util.*

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val connectivityManager = LocalContext.current.getSystemService(ConnectivityManager::class.java)
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val context = LocalContext.current

    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.requestData(
            connectivityManager,
            Geocoder(context, Locale.getDefault()),
            56.633331,
            47.866669
        )
    }

    var forecast by remember {
        mutableStateOf<Forecast?>(null)
    }

    when (uiState.value) {
        is HomeViewState.Forecast -> {
            forecast = (uiState.value as HomeViewState.Forecast).forecast
        }
        else -> {

        }
    }

    Box {
        HomeScreenBackground()
        TopAppBar()
        ForecastOverview(
            forecast = forecast,
            currentHour = currentHour
        )
    }
}

@Composable
fun HomeScreenBackground() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF311C7F),
                        darkPurple
                    ),
                    startY = screenHeight / 2
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_night_1),
            contentDescription = null
        )
    }
}

@Composable
fun TopAppBar(
    onProfileClicked: () -> Unit = { },
    onDrawerClicked: () -> Unit = { }
) {
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
            onClick = onProfileClicked
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
            onClick = onDrawerClicked
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_drawer),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun ForecastOverview(
    forecast: Forecast?,
    currentHour: Int
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 172.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Yoshkar-Ola".uppercase(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
            )
            Text(
                text = forecast?.dateTemperatureRange ?: "",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${forecast?.currentHourTemperature}°C",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp
            )
            Text(
                text = forecast?.weatherDescription ?: "",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 21.sp,
                textAlign = TextAlign.End
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(top = screenHeight / 2 - 30.dp)
    ) {
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
        ) {
            item {
                Spacer(modifier = Modifier.width(20.dp))
            }
            items(
                count = 2
            ) { idx ->
                Box(
                    modifier = Modifier
                        .size(172.dp, 215.dp)
                        .clip(RoundedCornerShape(22.dp))
                ) {
                    Image(
                        painter = painterResource(
                            if (idx % 2 == 0) R.drawable.bg_sun_location
                            else R.drawable.bg_snow_mountains
                        ),
                        contentDescription = null
                    )
                    Text(
                        text = if (idx % 2 == 0) "Jaipur 30°C" else "Chennai 22°C",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(
                                top = 24.dp,
                                start = 20.dp,
                                end = 20.dp
                            ),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(44.dp))
            }
        }
        Text(
            text = "Today",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp),
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontSize = 20.sp
        )
        LazyRow(
            modifier = Modifier.padding(top = 6.dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(start = 20.dp))
            }
            items(
                count = forecast?.dayTemperatureList?.size ?: 0
            ) { idx ->
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .background(darkPurple2)
                ) {
                    Text(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopCenter)
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                            .padding(5.dp),
                        text = "${
                            forecast?.dayTemperatureList?.get(idx)
                        }°C",
                        color = Color.Black
                    )
                    Text(
                        text = if (idx == currentHour) "Now" else "$idx:00",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .align(Alignment.BottomCenter),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(21.dp))
            }
        }
    }
}