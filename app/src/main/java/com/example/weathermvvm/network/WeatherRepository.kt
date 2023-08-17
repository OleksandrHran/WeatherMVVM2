package com.example.weathermvvm.network

import android.content.Context
import com.example.weathermvvm.database.dao.WeatherDao
import com.example.weathermvvm.database.data.WeatherEntity
import com.example.weathermvvm.network.data.WeatherResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor (
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService,
    @ApplicationContext private val context: Context
) {

    suspend fun getWeatherData(): List<WeatherEntity> {
        return withContext(Dispatchers.IO) {
            val isNetworkAvailable = NetworkUtils.isNetworkAvailable(context)
            if (isNetworkAvailable) {
                try {
                    val response: Response<WeatherResponse> = weatherService.getWeatherForecast(
                        "Kyiv",
                        "3b47ed3b92acbc7d2e25a4cc3d1afe02",
                        "metric"
                    )
                    if (response.isSuccessful) {
                        val weatherResponse = response.body()?.list ?: emptyList()
                        val weatherEntity = weatherResponse.map { data ->
                            WeatherEntity(
                                time = data.dt,
                                temp = data.main.temp,
                                icon = data.weather[0].icon
                            )
                        }
                        weatherDao.insertWeatherEntity(weatherEntity)
                    } else {
                        throw HttpException(response)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return@withContext weatherDao.getAllWeatherEntity()
        }
    }
}


