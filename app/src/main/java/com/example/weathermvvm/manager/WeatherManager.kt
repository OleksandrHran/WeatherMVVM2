package com.example.weathermvvm.manager

import android.content.Context
import com.example.weathermvvm.database.LocalDataSource
import com.example.weathermvvm.database.data.WeatherEntity
import com.example.weathermvvm.network.WeatherDataSource
import com.example.weathermvvm.network.data.MainData
import com.example.weathermvvm.network.data.WeatherData
import com.example.weathermvvm.network.data.WeatherDetail
import com.example.weathermvvm.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WeatherManager @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherDataSource: WeatherDataSource,
    @ApplicationContext private val context: Context
) {

    suspend fun fetchWeatherData(): List<WeatherData>? {
        return if (NetworkUtils.isNetworkAvailable(context)) {
            weatherDataSource.getWeatherData().map { weatherEntity ->
                convertToWeatherData(weatherEntity)
            }
        } else {
            localDataSource.getWeatherData()
        }
    }


    private fun convertToWeatherData(weatherEntity: WeatherEntity): WeatherData {
        return WeatherData(
            id = weatherEntity.id,
            dt = weatherEntity.time,
            main = MainData(temp = weatherEntity.temp),
            weather = listOf(WeatherDetail(icon = weatherEntity.icon))
        )
    }

     fun saveWeatherData(weatherData: List<WeatherData>) {
        localDataSource.saveWeatherData(weatherData)
    }

     fun loadWeatherDataFromDataBase(): List<WeatherData>{
        return localDataSource.getWeatherData()
    }
}