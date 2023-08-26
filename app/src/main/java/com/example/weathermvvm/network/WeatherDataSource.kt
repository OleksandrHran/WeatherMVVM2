package com.example.weathermvvm.network


import com.example.weathermvvm.database.data.WeatherEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataSource @Inject constructor(
    private val weatherService: WeatherService,
) {

    suspend fun getWeatherData(): List<WeatherEntity> {
        val response = weatherService.getWeatherForecast(
            "Kyiv",
            "3b47ed3b92acbc7d2e25a4cc3d1afe02",
            "metric",
            "ua",
            min = 0
        )
        return if (response.isSuccessful && !response.body()?.list.isNullOrEmpty()) {
            response.body()?.list?.map { weatherData ->
                WeatherEntity(
                    time = weatherData.dt,
                    temp = weatherData.main.temp,
                    icon = weatherData.weather[0].icon,
                    description = weatherData.weather[0].description,
                    temp_min = weatherData.main.temp_min
                )

            } ?: emptyList()
        } else {
            emptyList()
        }
    }
}