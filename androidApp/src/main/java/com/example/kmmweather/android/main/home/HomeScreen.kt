package com.example.kmmweather.android.main.home

import android.location.Geocoder
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmmweather.android.R
import com.example.kmmweather.android.darkPurple
import com.example.kmmweather.android.darkPurple2
import com.example.kmmweather.ui.Forecast
import java.util.*
import kotlin.math.roundToInt

// guide to MVI architecture: https://blog.mindorks.com/mvi-architecture-android-tutorial-for-beginners-step-by-step-guide/

enum class SwipeState {
    COLLAPSED, EXPANDED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val connectivityManager = LocalContext.current.getSystemService(ConnectivityManager::class.java)
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val context = LocalContext.current

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

    LaunchedEffect(Unit) {
        viewModel.state.collect {
            when (it) {
                is HomeViewState.Forecast -> {
                    forecast = it.forecast
                }
                else -> {

                }
            }
        }
    }

    Box {
        val swipeState = rememberSwipeableState(initialValue = SwipeState.COLLAPSED)
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        val alpha by remember {
            derivedStateOf { 1f - (swipeState.offset.value / screenHeight.value) }
        }

        HomeScreenBackground(alpha, screenHeight.value)
        TopAppBar()

        ForecastDetails(
            alpha = 1f - alpha,
            topPadding = screenHeight / 2,
            forecast = forecast
        )
        ForecastOverview(
            alpha = alpha,
            swipeState = swipeState,
            forecast = forecast,
            currentHour = currentHour
        )
    }
}

@Composable
fun HomeScreenBackground(alpha: Float, screenHeight: Float) {
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
            modifier = Modifier.alpha(alpha),
            painter = painterResource(id = R.drawable.bg_night_1),
            contentDescription = null
        )
        Image(
            modifier = Modifier.alpha(1f - alpha),
            painter = painterResource(id = R.drawable.bg_night_2),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForecastOverview(
    alpha: Float,
    swipeState: SwipeableState<SwipeState>,
    forecast: Forecast?,
    currentHour: Int
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .swipeable(
                state = swipeState,
                orientation = Orientation.Vertical,
                anchors = mapOf(
                    0f to SwipeState.COLLAPSED,
                    screenHeight.value to SwipeState.EXPANDED
                )
            )
            .offset {
                IntOffset(
                    x = 0,
                    y = swipeState.offset.value.roundToInt()
                )
            }
            .alpha(alpha),
    ) {
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
                    fontSize = 21.sp
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = screenHeight / 2 - 30.dp)
        ) {
            Text(
                text = "Swipe down for details",
                color = Color.White.copy(alpha = 0.6f),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Image(
                modifier = Modifier
                    .padding(top = 11.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null,
            )
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
            // https://stackoverflow.com/questions/65065285/jetpack-compose-lazycolumn-programmatically-scroll-to-item
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
}

@Composable
fun ForecastDetails(
    alpha: Float,
    topPadding: Dp,
    forecast: Forecast?
) {

    val yOffset = (1f - when {
        alpha > 1f -> 1f
        alpha < 0f -> 0f
        else -> alpha
    }) * topPadding.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topPadding - 50.dp)
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .offset(y = yOffset.dp)
            .alpha(alpha)
            .background(
                darkPurple
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 41.dp, start = 22.dp, end = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${forecast?.currentHourTemperature}°C",
                fontSize = 72.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_sun),
                    contentDescription = null
                )
                Text(
                    text = forecast?.weatherDescription ?: "",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}