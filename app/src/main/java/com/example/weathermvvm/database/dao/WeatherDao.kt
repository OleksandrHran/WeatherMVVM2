package com.example.weathermvvm.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathermvvm.database.data.WeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherEntity(weatherResponse: List<WeatherEntity>)

    @Query("SELECT * FROM weather_entity")
    fun getAllWeatherEntity(): List<WeatherEntity>
}