package com.example.weatherappjc

import androidx.lifecycle.Observer
import com.example.weatherappjc.models.Condition
import com.example.weatherappjc.models.Current
import com.example.weatherappjc.models.Location
import com.example.weatherappjc.models.WeatherResponse
import com.example.weatherappjc.repos.WeatherRepository
import com.example.weatherappjc.viewmodels.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @Mock
    lateinit var mockRepository: WeatherRepository

    @Mock
    lateinit var observer: Observer<List<WeatherResponse?>>

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        viewModel = WeatherViewModel(mockRepository)
        viewModel.weatherData.observeForever(observer)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `fetchWeatherData should update weatherData when repository returns data`() = runBlockingTest {
        // Given
        val cities = listOf("London", "Paris", "New York")
        val weatherResponse = WeatherResponse(
        location = Location(name = "London"),
        current = Current(temp_f = 70.0F, wind_kph = 15.0F, condition = Condition(text = "Sunny")))
        val weatherList = listOf(weatherResponse, weatherResponse, weatherResponse)

        // Mock the repository call to return a weatherResponse for each city
        cities.forEach { city ->
            whenever(mockRepository.fetchWeather(city)).thenReturn(weatherResponse)
        }

        // When
        viewModel.fetchWeatherData(cities)

        // Then
        // Observe the LiveData and verify the result
        verify(observer).onChanged(weatherList) // Verify the observer was updated with the correct list
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `fetchWeatherData should handle null values when repository returns null`() = runBlockingTest {
        // Given
        val cities = listOf("London", "Paris", "New York")
        val weatherResponse = null // Simulating a failed fetch (null result)

        // Mock the repository call to return null for each city
        cities.forEach { city ->
            whenever(mockRepository.fetchWeather(city)).thenReturn(weatherResponse)
        }

        // When
        viewModel.fetchWeatherData(cities)

        // Then
        // Observe the LiveData and verify the result is empty
        verify(observer).onChanged(emptyList()) // Should be empty if all results are null
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `fetchWeatherData should handle network failure gracefully`() = runBlockingTest {
        // Given
        val cities = listOf("London", "Paris", "New York")

        // Simulate a network failure by returning null or appropriate error state
        cities.forEach { city ->
            whenever(mockRepository.fetchWeather(city)).thenReturn(null)
        }

        // When
        viewModel.fetchWeatherData(cities)

// Then

    }}