package com.example.weathermvvm.di

import android.content.Context
import com.example.weathermvvm.database.dao.WeatherDao
import com.example.weathermvvm.network.WeatherRepository
import com.example.weathermvvm.network.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewComponent::class)
object MainViewModelModel {
    @Provides
    @ViewModelScoped
    fun provideWeatherRepository(
        weatherDao: WeatherDao,
        weatherService: WeatherService,
        @ApplicationContext context: Context
    ): WeatherRepository {
        return WeatherRepository(weatherDao, weatherService, context)
    }
}