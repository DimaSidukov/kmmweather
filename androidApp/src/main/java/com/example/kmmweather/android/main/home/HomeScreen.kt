package com.example.kmmweather.android.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kmmweather.android.R
import com.example.kmmweather.android.darkPurple

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

        ForecastOverview(
            alpha = alpha
        )


        HomeScreenBackground()
        TopAppBar()

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

@Composable
fun ForecastOverview(
    alpha: Float
) {
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
                .align(Alignment.BottomCenter)
                .padding(bottom = screenHeight / 2 + 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Swipe down for details",
                color = Color.White.copy(alpha = 0.6f),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            Image(
                modifier = Modifier.padding(top = 11.dp),
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ForecastDetails(
    alpha: Float
) {

}