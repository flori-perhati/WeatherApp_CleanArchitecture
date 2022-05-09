package com.example.weatherapp.domain.use_case

import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherByCoordinatesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(lat: Double, lon: Double): Flow<Resource<CityWeather>> = flow {
        emit(Resource.Loading())
        val response = repository.cityWeatherByCoordinates(lat, lon)
        emit(response)
    }
}