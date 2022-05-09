package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.utility.Resource

interface WeatherRepository {

    suspend fun cityWeatherByName(
        city: String
    ): Resource<CityWeather>

    suspend fun cityWeatherByCoordinates(
        lat: Double,
        lon: Double
    ): Resource<CityWeather>
}