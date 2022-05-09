package com.example.weatherapp.data.repository

import com.example.weatherapp.data.dto.toCityWeather
import com.example.weatherapp.data.service.WeatherService
import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.utility.Resource
import com.example.weatherapp.utility.key.store.KeyStoreManager
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val keyStoreManager: KeyStoreManager
) : WeatherRepository {

    override suspend fun cityWeatherByName(city: String): Resource<CityWeather> {
        return try {
            val cityWeather = weatherService.cityWeatherByName(city, keyStoreManager.weatherApiKey, "metric").toCityWeather()
            Resource.Success(cityWeather)
        } catch(e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch(e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }

    override suspend fun cityWeatherByCoordinates(
        lat: Double,
        lon: Double
    ): Resource<CityWeather> {
        return try {
            val cityWeather = weatherService.cityWeatherByCoordinates(lat, lon, keyStoreManager.weatherApiKey, "metric").toCityWeather()
            Resource.Success(cityWeather)
        } catch(e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch(e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }
}