package com.example.weatherapp.domain.use_case

import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherByNameUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(city: String): Flow<Resource<CityWeather>> = flow {
        emit(Resource.Loading())
        val response = repository.cityWeatherByName(city)
        emit(response)
    }
}