package com.example.weathermvvm.network.data

data class WeatherData(
    val id: Int,
    val dt: Long,
    val description: String,
    val main: MainData,
    val weather: List<WeatherDetail>
)

