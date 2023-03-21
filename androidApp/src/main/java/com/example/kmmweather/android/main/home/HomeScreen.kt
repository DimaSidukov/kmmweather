package com.example.kmmweather.android.main.home

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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmmweather.android.R
import com.example.kmmweather.android.darkPurple
import com.example.kmmweather.android.darkPurple2
import kotlin.math.roundToInt

// guide to MVI architecture: https://blog.mindorks.com/mvi-architecture-android-tutorial-for-beginners-step-by-step-guide/


enum class SwipeState {
    COLLAPSED, EXPANDED
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun HomeScreen() {
    Box {

        val swipeableState = rememberSwipeableState(initialValue = SwipeState.COLLAPSED)
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        val alpha by remember {
            derivedStateOf { 1f - (swipeableState.offset.value / screenHeight.value) }
        }

        HomeScreenBackground()
        TopAppBar()

        ForecastOverview(
            alpha = alpha,
            swipeableState = swipeableState
        )
        ForecastDetails(
            alpha = alpha,
            swipeableState = swipeableState
        )
    }
}

@Composable
fun HomeScreenBackground() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        var imageHeight by remember { mutableStateOf(0) }
        val density = LocalDensity.current.density

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(top = if ((imageHeight / density).dp >= 20.dp) ((imageHeight / density).dp - 60.dp) else 0.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF311C7F),
                            darkPurple
                        )
                    )
                )
        )
        Image(
            modifier = Modifier.onGloballyPositioned {
                imageHeight = it.size.height
            },
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForecastOverview(
    alpha: Float,
    swipeableState: SwipeableState<SwipeState>,

    ) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .swipeable(
                state = swipeableState,
                orientation = Orientation.Vertical,
                anchors = mapOf(
                    0f to SwipeState.COLLAPSED,
                    screenHeight.value to SwipeState.EXPANDED
                )
            )
            .offset {
                IntOffset(
                    x = 0,
                    y = swipeableState.offset.value.roundToInt()
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
                    text = "Pekin".uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                )
                Text(
                    text = "7 Nov 2022 Lun  20°C/29°C",
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
                    text = "24°C",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp
                )
                Text(
                    text = "Clear",
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
                    count = 4
                ) { idx ->
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(darkPurple2)
                    ) {
                        Image(
                            painter = painterResource(
                                when (idx) {
                                    3 -> R.drawable.ic_drizzle
                                    2 -> R.drawable.ic_heavy_rain
                                    else -> R.drawable.ic_sun_behind_cloud
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .align(Alignment.TopCenter)
                        )
                        Text(
                            text = if (idx == 0) "Now" else "$idx:00PM",
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForecastDetails(
    alpha: Float,
    swipeableState: SwipeableState<SwipeState>
) {


}