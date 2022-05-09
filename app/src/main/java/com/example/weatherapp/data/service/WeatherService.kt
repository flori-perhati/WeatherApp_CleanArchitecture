package com.example.weatherapp.data.service

import com.example.weatherapp.data.dto.CityWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    suspend fun cityWeatherByName(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): CityWeatherDto

    @GET("/data/2.5/weather")
    suspend fun cityWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): CityWeatherDto
}