package com.example.weatherapp.feature_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.domain.use_case.WeatherUseCases
import com.example.weatherapp.utility.DispatcherProvider
import com.example.weatherapp.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val weatherUseCases: WeatherUseCases
) : ViewModel() {

    private val _state = MutableSharedFlow<Resource<CityWeather>>()
    val state = _state.asSharedFlow()

    // 2-way data binding to retrieve the user input
    var cityName: String = ""

    fun retrieveWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch(dispatchers.main) {
            weatherUseCases.weatherByCoordinates(lat, lon).collect { _state.emit(it) }
        }
    }

    fun retrieveWeatherByName() {
        if (cityName.length < 3) {
            viewModelScope.launch(dispatchers.main) { _state.emit(Resource.Error("City name is too short!")) }
            return
        }

        viewModelScope.launch(dispatchers.main) {
            weatherUseCases.weatherByName(cityName).collect { _state.emit(it) }
        }
    }
}