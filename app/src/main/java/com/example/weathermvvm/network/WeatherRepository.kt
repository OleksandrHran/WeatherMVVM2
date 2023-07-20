package com.example.weathermvvm.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.weathermvvm.database.dao.WeatherDao
import com.example.weathermvvm.database.data.WeatherEntity
import com.example.weathermvvm.network.data.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class WeatherRepository(
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService,
    private val context: Context
) {

    suspend fun getWeatherData(): List<WeatherEntity> {
        return withContext(Dispatchers.IO) {

            if (isNetworkAvailable(context)) {
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

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}


