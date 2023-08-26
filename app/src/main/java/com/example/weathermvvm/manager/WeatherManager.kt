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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class WeatherManager @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val weatherDataSource: WeatherDataSource,
    @ApplicationContext private val context: Context
) {

    suspend fun fetchWeatherData(): List<WeatherData>? {

        return if (NetworkUtils.isNetworkAvailable(context)) {
            val responseWeather = weatherDataSource.getWeatherData()
            val convertWeatherDta = responseWeather.map { weatherEntity ->
                convertToWeatherData(weatherEntity)
            }
            withContext(Dispatchers.IO) {
                localDataSource.saveWeatherData(convertWeatherDta)
            }
            convertWeatherDta
        } else {
            withContext(Dispatchers.IO) {
                localDataSource.getWeatherData()
            }
        }
    }

    private fun convertToWeatherData(weatherEntity: WeatherEntity): WeatherData {
        return WeatherData(
            id = weatherEntity.id,
            dt = weatherEntity.time,
            main = MainData(temp = weatherEntity.temp, temp_min = weatherEntity.temp),
            weather = listOf(WeatherDetail(icon = weatherEntity.icon, description = weatherEntity.description.toString())),
            description = weatherEntity.description.toString()
        )
    }
}