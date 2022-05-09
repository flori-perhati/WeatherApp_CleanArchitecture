package com.example.weatherapp.di

import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.data.service.WeatherService
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.use_case.WeatherByCoordinatesUseCase
import com.example.weatherapp.domain.use_case.WeatherByNameUseCase
import com.example.weatherapp.domain.use_case.WeatherUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherNetworkModule {

    @Singleton
    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository {
        return weatherRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideWeatherUseCases(
        weatherByName: WeatherByNameUseCase,
        weatherByCoordinates: WeatherByCoordinatesUseCase
    ): WeatherUseCases {
        return WeatherUseCases(
            weatherByName,
            weatherByCoordinates
        )
    }
}

