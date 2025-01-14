package com.example.weatherappjc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappjc.models.WeatherResponse
import com.example.weatherappjc.repos.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherResponse?>>()
    val weatherData: LiveData<List<WeatherResponse?>> = _weatherData

    fun fetchWeatherData(cities: List<String>) {
        viewModelScope.launch {
            val weatherList = cities.map { city ->
                async { repository.fetchWeather(city) }
            }.awaitAll()
            _weatherData.postValue(weatherList)
        }
    }
}
