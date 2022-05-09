package com.example.weatherapp.domain.use_case

data class WeatherUseCases(
    val weatherByName: WeatherByNameUseCase,
    val weatherByCoordinates: WeatherByCoordinatesUseCase
)
