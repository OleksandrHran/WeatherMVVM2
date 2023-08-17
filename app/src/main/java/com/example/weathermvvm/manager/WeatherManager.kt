package com.example.weathermvvm.manager

import com.example.weathermvvm.database.LocalDataSource
import com.example.weathermvvm.network.NetworkDataSource
import com.example.weathermvvm.network.WeatherRepository
import com.example.weathermvvm.network.data.MainData
import com.example.weathermvvm.network.data.WeatherData
import com.example.weathermvvm.network.data.WeatherDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherManager @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val weatherRepository: WeatherRepository
) {
    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    suspend fun fetchWeatherData(): List<WeatherData>? {
        val weatherData = networkDataSource.getWeatherData()
        weatherData?.let {
            backgroundScope.launch {
                localDataSource.saveWeatherData(it)
            }
        }
        val weatherEntities = weatherRepository.getWeatherData()
        return weatherEntities.map { entity ->
            WeatherData(
                id = entity.id,
                dt = entity.time,
                main = MainData(temp = entity.temp),
                weather = listOf(WeatherDetail(icon = entity.icon))
            )
        }
    }
}