package com.example.weathermvvm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvvm.R
import com.example.weathermvvm.utils.WeatherUtils
import com.example.weathermvvm.network.data.WeatherData


class HorizontalRecycleView(private var data: List<WeatherData>) :
    RecyclerView.Adapter<HorizontalRecycleView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newWeatherList: List<WeatherData>) {
        data = newWeatherList.subList(0, 24)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val weather = data[position]
        holder.time.text = WeatherUtils.getTime(weather.dt)
        holder.temperature.text = weather.main.temp.toInt().toString() + "°C"
        val iconResource = WeatherUtils.getIconId(weather.weather[0].icon)
        holder.weather.setImageResource(iconResource)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.time)
        val temperature: TextView = itemView.findViewById(R.id.temperature)
        val weather: ImageView = itemView.findViewById(R.id.weather)
    }
}
