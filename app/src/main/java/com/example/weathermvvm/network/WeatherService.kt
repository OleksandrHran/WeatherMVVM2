package com.example.weathermvvm.network

import com.example.weathermvvm.network.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("min") min: Int
    ): Response<WeatherResponse>
}


