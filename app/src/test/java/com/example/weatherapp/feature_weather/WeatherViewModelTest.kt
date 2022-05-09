package com.example.weatherapp.feature_weather

import app.cash.turbine.test
import com.example.weatherapp.TestDispatchers
import com.example.weatherapp.data.repository.FakeWeatherRepositoryImpl
import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.domain.use_case.WeatherByCoordinatesUseCase
import com.example.weatherapp.domain.use_case.WeatherByNameUseCase
import com.example.weatherapp.domain.use_case.WeatherUseCases
import com.example.weatherapp.utility.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private lateinit var emptyCityWeather: CityWeather
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fakeWeatherRepositoryImpl: FakeWeatherRepositoryImpl

    @Before
    fun setup() {
        emptyCityWeather = CityWeather("", "", "", "", "", "", "")
        fakeWeatherRepositoryImpl = FakeWeatherRepositoryImpl()
        //
        val testDispatchers = TestDispatchers()
        val weatherByNameUseCase = WeatherByNameUseCase(fakeWeatherRepositoryImpl)
        val weatherByCoordinatesUseCase = WeatherByCoordinatesUseCase(fakeWeatherRepositoryImpl)
        //
        viewModel = WeatherViewModel(
            testDispatchers,
            WeatherUseCases(weatherByNameUseCase, weatherByCoordinatesUseCase)
        )
    }

    @Test
    fun `retrieve weather by coordinates, returns Resource_Error`() = runBlocking {
        fakeWeatherRepositoryImpl.setShouldReturnNetworkError(true)

        viewModel.retrieveWeatherByCoordinates(123.3, 4324.4)

        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Resource.Error<CityWeather>("Error"))
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `retrieve weather by coordinates, returns Resource_Success`() = runBlocking {
        fakeWeatherRepositoryImpl.setShouldReturnNetworkError(false)

        viewModel.retrieveWeatherByCoordinates(123.3, 4324.4)

        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Resource.Success(emptyCityWeather))
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `retrieve weather by name, returns Resource_Error, city name too short`() = runBlocking {
        fakeWeatherRepositoryImpl.setShouldReturnNetworkError(false)

        viewModel.cityName = "Ti" // must be at least 3 characters long
        viewModel.retrieveWeatherByName()

        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Resource.Error<CityWeather>("City name is too short!"))
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `retrieve weather by name, returns Resource_Error`() = runBlocking {
        fakeWeatherRepositoryImpl.setShouldReturnNetworkError(true)

        viewModel.cityName = "Tirana"
        viewModel.retrieveWeatherByName()

        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Resource.Error<CityWeather>("Error"))
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `retrieve weather by name, returns Resource_Success`() = runBlocking {
        fakeWeatherRepositoryImpl.setShouldReturnNetworkError(false)

        viewModel.cityName = "Tirana"
        viewModel.retrieveWeatherByName()

        val job = launch {
            viewModel.state.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Resource.Success(emptyCityWeather))
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }
}