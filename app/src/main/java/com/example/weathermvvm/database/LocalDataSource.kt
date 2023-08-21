package com.example.weathermvvm.database

import com.example.weathermvvm.database.dao.WeatherDao
import com.example.weathermvvm.database.data.WeatherEntity
import com.example.weathermvvm.network.data.MainData
import com.example.weathermvvm.network.data.WeatherData
import com.example.weathermvvm.network.data.WeatherDetail
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val weatherDao: WeatherDao) {

    fun getWeatherData(): List<WeatherData> {
        return weatherDao.getAllWeatherEntity().map { weatherEntity ->
            WeatherData(
                id = weatherEntity.id,
                dt = weatherEntity.time,
                main = MainData(temp = weatherEntity.temp),
                weather = listOf(WeatherDetail(icon = weatherEntity.icon))
            )
        }
    }

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