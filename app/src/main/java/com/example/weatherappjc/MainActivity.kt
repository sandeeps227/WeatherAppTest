package com.example.weatherappjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: WeatherViewModel = hiltViewModel()
            WeatherScreen(viewModel)
            viewModel.fetchWeatherData(listOf("London", "New York", "Tokyo", "Mumbai", "Sydney"))
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val weatherList by viewModel.weatherData.observeAsState(emptyList())

    LazyColumn {
        items(weatherList) { weather ->
            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("City: ${weather.location.name}")
                    Text("Temp (F): ${weather.current.temp_f}")
                    Text("Wind (kph): ${weather.current.wind_kph}")
                    Text("Condition: ${weather.current.condition.text}")
                }
            }
        }
    }
}
