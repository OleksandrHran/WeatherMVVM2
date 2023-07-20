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
import java.util.concurrent.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherEntity(weatherResponse: List<WeatherEntity>)

    @Query("SELECT * FROM weather_entity")
    fun getAllWeatherEntity(): List<WeatherEntity>
}