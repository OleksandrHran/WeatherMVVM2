package com.example.weathermvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermvvm.network.WeatherRepository
import com.example.weathermvvm.database.data.WeatherEntity
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherEntity>>()
    val weatherData: LiveData<List<WeatherEntity>> = _weatherData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var cachedWeatherData: List<WeatherEntity>? = null

    init {
        viewModelScope.launch {
            cachedWeatherData = weatherRepository.getWeatherData()
            _weatherData.postValue(cachedWeatherData)
        }
    }

    suspend fun getWeatherData(): List<WeatherEntity>? {
        viewModelScope.launch {
            try {
                val weatherResponse = weatherRepository.getWeatherData()
                _weatherData.postValue(weatherResponse)
                _error.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue("Failed to fetch weather data")
            }
        }
        return cachedWeatherData
    }
}