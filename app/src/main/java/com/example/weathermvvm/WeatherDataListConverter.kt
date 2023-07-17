package com.example.weathermvvm

import androidx.room.TypeConverter
import com.example.weathermvvm.network.data.WeatherData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherDataListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromWeatherDataList(weatherDataList: List<WeatherData>): String {
        return gson.toJson(weatherDataList)
    }

    @TypeConverter
    fun toWeatherDataList(weatherDataListString: String): List<WeatherData>{
        val listType = object : TypeToken<List<WeatherData>>() {}.type
        return gson.fromJson(weatherDataListString, listType)
    }
}