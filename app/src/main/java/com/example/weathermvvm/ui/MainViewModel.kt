package com.example.weathermvvm.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermvvm.network.WeatherRepository
import com.example.weathermvvm.network.data.MainData
import com.example.weathermvvm.network.data.WeatherData
import com.example.weathermvvm.network.data.WeatherDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val viewModelManager: ViewModelManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>> = _weatherData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        viewModelScope.launch {
            try {
                val weatherResponse = weatherRepository.getWeatherData()
                val weatherDataList = weatherResponse.map { weatherEntity ->
                    WeatherData(
                        id = weatherEntity.id,
                        dt = weatherEntity.time,
                        main = MainData(temp = weatherEntity.temp),
                        weather = listOf(WeatherDetail(icon = weatherEntity.icon))
                    )
                }

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
            val weatherData = viewModelManager.fetchWeatherData()
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