package com.example.weatherapp.data.repository

import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.utility.Resource

class FakeWeatherRepositoryImpl : WeatherRepository {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun cityWeatherByName(city: String): Resource<CityWeather> {
        return if (shouldReturnNetworkError)
            Resource.Error("Error")
        else
            Resource.Success(CityWeather("", "", "", "", "", "", ""))
    }

    override suspend fun cityWeatherByCoordinates(lat: Double, lon: Double): Resource<CityWeather> {
        return if (shouldReturnNetworkError)
            Resource.Error("Error")
        else
            Resource.Success(CityWeather("", "", "", "", "", "", ""))
    }
}