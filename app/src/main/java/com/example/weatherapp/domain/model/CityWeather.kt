package com.example.weatherapp.domain.model

data class CityWeather(
    val cityName: String,
    val imagePath: String,
    val temp: String,
    val conditions: String,
    val windSpeed: String,
    val pressure: String,
    val humidity: String
)
