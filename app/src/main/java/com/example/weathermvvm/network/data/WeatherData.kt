package com.example.weathermvvm.network.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class WeatherData(
    val id: Int,

    val dt: Long,
    val main: String?,
    val weather: String?
)

