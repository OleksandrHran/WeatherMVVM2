package com.example.weathermvvm.network.data

data class WeatherResponse(
     val id: Long = 0,
     val list: List<WeatherData>
)

