package com.example.weatherapp.data.dto

import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.utility.constant.Server

data class CityWeatherDto(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

fun CityWeatherDto.toCityWeather(): CityWeather {
    val cityWeather = weather[0]
    val cityName = "$name, ${sys.country}"
    val imagePath = "${Server.IMAGE_BASE_URL}/img/wn/${cityWeather.icon}@2x.png"
    return CityWeather(
        cityName = cityName,
        imagePath = imagePath,
        temp = "${main.temp}°",
        conditions = cityWeather.main,
        windSpeed = "${wind.speed} km/h",
        pressure = "${main.pressure} hPa",
        humidity = "${main.humidity} %"
    )
}