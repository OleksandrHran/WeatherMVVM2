package com.example.weathermvvm.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_entity")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: Long,
    val icon: String,
    val temp: Double
)