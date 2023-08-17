package com.example.weathermvvm.database

import com.example.weathermvvm.database.dao.WeatherDao
import com.example.weathermvvm.database.data.WeatherEntity
import com.example.weathermvvm.network.data.WeatherData
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val weatherDao: WeatherDao) {

    fun saveWeatherData(weatherData: List<WeatherData>) {
        val weatherEntityList = weatherData.map { data ->
            WeatherEntity(
                time = data.dt,
                temp = data.main.temp,
                icon = data.weather[0].icon
            )
        }
        weatherDao.insertWeatherEntity(weatherEntityList)
    }
}