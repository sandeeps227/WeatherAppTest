package com.example.weatherappjc

import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun fetchWeather(city: String): WeatherResponse {
        return weatherApi.getWeather("520916eb3f46442ca1c12926221402", city)
    }
}


