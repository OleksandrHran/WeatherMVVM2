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

    private val _currentTemperature = MutableLiveData<String>()
    val currentTemperature: LiveData<String> = _currentTemperature

    private val _currentWeatherCondition = MutableLiveData<String>()
    val currentWeatherCondition: LiveData<String> = _currentWeatherCondition


    fun getWeatherData() {
        viewModelScope.launch {
            val fetchedWeatherData = weatherManager.fetchWeatherData()
            _weatherData.postValue(fetchedWeatherData)

            val currentTemp = fetchedWeatherData?.firstOrNull()?.main?.temp ?: 0.0
            val roundedTemp = currentTemp.toInt()
            _currentTemperature.postValue(roundedTemp.toString())

            val currentCondition = fetchedWeatherData?.firstOrNull()?.description ?: ""
            _currentWeatherCondition.postValue(currentCondition)
        }
    }
}