package com.example.kmmweather.android.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kmmweather.android.core.BottomNavigationItem
import com.example.kmmweather.android.darkPurple
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavHostController) {

    var tfValue: String by remember { mutableStateOf("") }
    var scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(darkPurple)
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter the city name:",
            modifier = Modifier.padding(top = 100.dp),
            color = Color.White,
            fontSize = 14.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.5f)),
            value = tfValue,
            onValueChange = {
                tfValue = it
            },
            placeholder = {
                Text(text = "Yoshkar-Ola")
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
        OutlinedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(50)),
            onClick = {
                scope.launch {
                    val coords = viewModel.getCoordinates(tfValue)
                    navController.navigate("home/${coords.first}/${coords.second}/true") {
                        popUpTo("home/${coords.first}/${coords.second}/true")
                    }
                }
            }
        ) {
            Text(
                text = "Apply",
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
            )
        }
    }
}