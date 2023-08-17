package com.example.weathermvvm.network

import com.example.weathermvvm.network.data.WeatherData
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val weatherService: WeatherService) {

    suspend fun getWeatherData(): List<WeatherData>? {
        try {
            val response = weatherService.getWeatherForecast(
                "Kyiv",
                "3b47ed3b92acbc7d2e25a4cc3d1afe02",
                "metric"
            )
            if (response.isSuccessful)
                return response.body()?.list?.subList(0, 24)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}