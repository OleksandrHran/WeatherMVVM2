package com.example.weathermvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvvm.R
import com.example.weathermvvm.WeatherDatabase
import com.example.weathermvvm.network.WeatherRepository
import com.example.weathermvvm.network.WeatherServiceFactory
import com.example.weathermvvm.network.data.MainData
import com.example.weathermvvm.network.data.WeatherData
import com.example.weathermvvm.network.data.WeatherDetail
import com.example.weathermvvm.recycle.HorizontalRecycleView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecycleView
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        val weatherDatabase = WeatherDatabase.getDataBase(applicationContext)
        val weatherDao = weatherDatabase.weatherDao()

        recyclerView = findViewById(R.id.horizontal_recyclerview)
        adapter = HorizontalRecycleView(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        val weatherService = WeatherServiceFactory.create()
        val weatherRepository = WeatherRepository(weatherDao, weatherService, applicationContext)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(weatherRepository)
        )[MainViewModel::class.java]
        viewModel.weatherData.observe(this) { weatherEntities ->
            val weatherDataList = weatherEntities.map { weatherEntity ->
                WeatherData(
                    id = weatherEntity.id,
                    dt = weatherEntity.time,
                    main = MainData(temp = weatherEntity.temp),
                    weather = listOf(WeatherDetail(icon = weatherEntity.icon))
                )
            }
            adapter.updateData(weatherDataList)
        }
        lifecycleScope.launch {
            viewModel.getWeatherData()
        }
    }
}
