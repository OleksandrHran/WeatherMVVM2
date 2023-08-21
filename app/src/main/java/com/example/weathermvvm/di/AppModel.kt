package com.example.weathermvvm.di

import android.content.Context
import com.example.weathermvvm.database.LocalDataSource
import com.example.weathermvvm.manager.WeatherManager
import com.example.weathermvvm.network.WeatherDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewComponent::class)
object AppModel {
    @Provides
    @Singleton
    fun provideWeatherManager(

        localDataSource: LocalDataSource,
        weatherDataSource: WeatherDataSource,
        @ApplicationContext  context: Context

    ): WeatherManager {
        return WeatherManager(localDataSource, weatherDataSource, context )
    }
}