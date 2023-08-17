package com.example.weathermvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermvvm.manager.WeatherManager
import com.example.weathermvvm.network.data.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherManager: WeatherManager,
) : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>> = _weatherData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        viewModelScope.launch {
            try {
                val weatherDataList = weatherManager.fetchWeatherData()
                _weatherData.postValue(weatherDataList)
                _error.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue("Failed to fetch weather data")
            }
        }
    }

    private suspend fun fetchWeatherData() {
        try {
            val weatherData = weatherManager.fetchWeatherData()
            if (weatherData != null) {
                _weatherData.postValue(weatherData)
            } else {
                _error.postValue("Failed to fetch weather data")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _error.postValue("An error occurred while fetching weather data")
        }
    }

    fun startFetchingWeatherData() {
        viewModelScope.launch {
            fetchWeatherData()
        }
    }
}