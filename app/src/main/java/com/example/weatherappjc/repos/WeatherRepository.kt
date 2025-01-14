package com.example.weatherappjc.repos

import com.example.weatherappjc.apis.WeatherApi
import com.example.weatherappjc.models.WeatherResponse
import com.example.weatherappjc.utils.AppConstants
import com.example.weatherappjc.utils.NetworkUtils
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApi,
    private val networkUtils: NetworkUtils
) {
    suspend fun fetchWeather(city: String): WeatherResponse? {
        if (!networkUtils.isNetworkAvailable()) {
            return null // Return null if there is no internet connection
        }
        return try {
            val response = apiService.getWeather(AppConstants.API_KEY, city)
            response // Return the WeatherResponse directly on success
        } catch (e: Exception) {
            // Log or handle the error, return null to indicate failure
            null
        }
    }
}


