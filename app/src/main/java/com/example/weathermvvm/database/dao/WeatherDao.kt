package com.example.weathermvvm.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathermvvm.database.data.WeatherEntity
import com.example.weathermvvm.network.data.MainData
import com.example.weathermvvm.network.data.WeatherData
import com.example.weathermvvm.network.data.WeatherDetail
import com.example.weathermvvm.network.data.WeatherResponse

@Dao
interface WeatherDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMainData(mainData: WeatherEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertWeatherDetail(weatherDetail: WeatherDetail)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertWeatherData(weatherData: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertWeatherResponse(weatherResponse: WeatherEntity)

    @Query("SELECT * FROM weather_entity")

    fun getAllWeatherResponses(): LiveData<List<WeatherEntity>>

}