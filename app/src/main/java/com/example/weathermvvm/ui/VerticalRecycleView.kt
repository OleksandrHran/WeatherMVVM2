package com.example.weathermvvm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvvm.R
import com.example.weathermvvm.network.data.WeatherData
import com.example.weathermvvm.utils.WeatherUtils

class VerticalRecycleView(private var data: List<WeatherData>) :
    RecyclerView.Adapter<VerticalRecycleView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.col, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newWeatherList: List<WeatherData>) {
        data = newWeatherList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val weather = data[position]
        holder.day.text = WeatherUtils.getDayFromDate(weather.dt)
        holder.temperature.text = weather.main.temp.toInt().toString() + "°C"
        holder.minTemperature.text = weather.main.temp.toInt().toString()
        val iconResource = WeatherUtils.getIconId(weather.weather[0].icon)
        holder.weather.setImageResource(iconResource)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day)
        val temperature: TextView = itemView.findViewById(R.id.max_temperature)
        val minTemperature: TextView = itemView.findViewById(R.id.min_temperature)
        val weather: ImageView = itemView.findViewById(R.id.weather)
    }
}
