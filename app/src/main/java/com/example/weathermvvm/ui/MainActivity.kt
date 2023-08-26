package com.example.weathermvvm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvvm.R
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecycleView
    private lateinit var adapterVertical: VerticalRecycleView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)


        recyclerView = findViewById(R.id.horizontal_recyclerview)
        adapter = HorizontalRecycleView(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        recyclerView = findViewById(R.id.vertical_recyclerview)
        adapterVertical = VerticalRecycleView(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapterVertical

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.weatherData.observe(this) { weatherDataList ->
            adapter.updateData(weatherDataList)
            adapterVertical.updateData(weatherDataList)
        }
        viewModel.currentTemperature.observe(this) { temperature ->
            val temperatureTextView: TextView = findViewById(R.id.temperature)
            temperatureTextView.text = temperature
        }
        viewModel.currentWeatherCondition.observe(this) { condition ->
            val weatherConditionTextView: TextView = findViewById(R.id.condition_weather)
            weatherConditionTextView.text = condition
        }

        val openMapButton: Button = findViewById(R.id.map)
        openMapButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        Configuration.getInstance().userAgentValue = packageName


        viewModel.getWeatherData()
    }
}
