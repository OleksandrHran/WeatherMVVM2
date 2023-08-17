package com.example.weathermvvm.ui

import com.example.weathermvvm.database.LocalDataSource
import com.example.weathermvvm.network.NetworkDataSource
import com.example.weathermvvm.network.data.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelManager @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) {
    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    suspend fun fetchWeatherData(): List<WeatherData>? {
        val weatherData = networkDataSource.getWeatherData()
        weatherData?.let {
            backgroundScope.launch {
                localDataSource.saveWeatherData(it)
            }
        }
        return weatherData
    }
}