package com.example.weathermvvm.di

import com.example.weathermvvm.network.WeatherService
import com.example.weathermvvm.network.WeatherServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherService(): WeatherService{
        return WeatherServiceFactory.create()
    }
}