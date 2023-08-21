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

    fun startFetchingWeatherData() {
        viewModelScope.launch {
            try {
                val weatherData = weatherManager.fetchWeatherData()
                if (weatherData != null) {
                    _weatherData.postValue(weatherData)
                    weatherManager.saveWeatherData(weatherData)
                }else{
                    loadWeatherFromDatabase()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private  fun loadWeatherFromDatabase() {
        val weatherFromDataBase = weatherManager.loadWeatherDataFromDataBase()
        if (weatherFromDataBase.isEmpty()) {
            _weatherData.postValue(weatherFromDataBase)
        }
    }
}