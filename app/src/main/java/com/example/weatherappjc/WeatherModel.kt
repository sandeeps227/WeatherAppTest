package com.example.weatherappjc

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String
)

data class Current(
    val temp_f: Float,
    val wind_kph: Float,
    val condition: Condition
)

data class Condition(
    val text: String
)
