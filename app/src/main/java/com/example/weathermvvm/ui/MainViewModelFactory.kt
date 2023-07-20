package com.example.weathermvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermvvm.network.WeatherRepository
import com.example.weathermvvm.database.dao.WeatherDao

class MainViewModelFactory(private val weatherRepository: WeatherRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(weatherRepository) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}