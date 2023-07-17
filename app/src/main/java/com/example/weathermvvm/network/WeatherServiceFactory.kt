package com.example.weathermvvm.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherServiceFactory {
    companion object {
        private const val baseUrl = "https://api.openweathermap.org/data/2.5/"

        fun create(): WeatherService {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WeatherService::class.java)

        }
    }
}
