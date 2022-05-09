package com.example.weatherapp.feature_weather

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.domain.model.CityWeather
import com.example.weatherapp.utility.Resource
import com.example.weatherapp.utility.extension.*
import com.example.weatherapp.utility.fragment.InitializationFragment
import com.example.weatherapp.utility.permission.Permission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherFragment: InitializationFragment<FragmentWeatherBinding, WeatherViewModel>() {

    /**
     * InitializationFragment implementation
     */
    override val layoutId: Int = R.layout.fragment_weather
    override val viewModel: WeatherViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        viewBinding.btnSearch.setOnClickListener { performSearch() }
        collectState()
        //
        requestLocationPermission()
    }

    private fun performSearch() {
        viewBinding.etSearch.clearFocus()
        viewBinding.etSearch.hideKeyboard()
        viewModel.retrieveWeatherByName()
    }

    private fun requestLocationPermission() {
        permissionManager
            .request(Permission.Location)
            .rationale("We need location permission to show weather for the current location!")
            .checkPermission { granted ->
                if (granted)
                    retrieveLocationCoordinates()
                else
                    requireContext().shortToast("Location permission not granted")
            }
    }

    private fun retrieveLocationCoordinates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            //
            if (location != null)
                viewModel.retrieveWeatherByCoordinates(location.latitude, location.longitude)
            else
                showWarning("Current location could not be found!\nTry entering the city name in the search bar.")
        }
    }

    private fun collectState() {
        collectLifecycleFlow(viewModel.state) { state ->
            when(state) {
                is Resource.Loading -> progressDialog.show()
                is Resource.Error -> showWarning(state.message)
                is Resource.Success -> showWeather(state.data)
            }
        }
    }

    private fun showWeather(cityWeather: CityWeather?) {
        progressDialog.dismiss()
        //
        viewBinding.warningView.gone()
        viewBinding.weatherLayout.visible()
        //
        cityWeather?.let {
            viewBinding.etSearch.setText(cityWeather.cityName)
            viewBinding.imgWeather.loadImageFromPath(cityWeather.imagePath)
            viewBinding.tvTemperature.text = cityWeather.temp
            viewBinding.tvCondition.text = cityWeather.conditions
            viewBinding.tvWind.text = cityWeather.windSpeed
            viewBinding.tvPressure.text = cityWeather.pressure
            viewBinding.tvHumidity.text = cityWeather.humidity
        } ?: showWarning("No data retrieved!")

    }

    private fun showWarning(message: String?) {
        progressDialog.dismiss()
        //
        viewBinding.warningView.visible()
         viewBinding.weatherLayout.gone()
        //
        message?.let {
            viewBinding.warningView.warningText = message
        } ?: run { viewBinding.warningView.warningText = "Something went wrong!" }
    }
}