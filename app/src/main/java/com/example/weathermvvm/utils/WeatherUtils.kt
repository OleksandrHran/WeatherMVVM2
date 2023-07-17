package com.example.weathermvvm.utils

import com.example.weathermvvm.R
import java.text.SimpleDateFormat
import java.util.*

object WeatherUtils {

    fun getTime(timestamp: Long): String {
        val date = Date(timestamp * 1000L)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(date)
    }

    fun getIconId(iconCode: String): Int {
        return when (iconCode) {

            "01d" -> R.drawable.sun
            "01n" -> R.drawable.moon
            "02d" -> R.drawable.clouds
            "02n" -> R.drawable.cloudy_night
            "03d", "03n", "04d", "04n" -> R.drawable.cloud
            "09d", "09n" -> R.drawable.raindrops
            "10d", "10n" -> R.drawable.raining
            "11d", "11n" -> R.drawable.thunderbolt
            "13d", "13n" -> R.drawable.snowflake
            "50d", "50n" -> R.drawable.wind
            else -> R.drawable.sun
        }
    }
}
