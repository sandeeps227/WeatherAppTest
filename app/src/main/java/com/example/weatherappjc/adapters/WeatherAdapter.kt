package com.example.weatherappjc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappjc.R
import com.example.weatherappjc.models.WeatherResponse

class WeatherAdapter(private val weatherList: List<WeatherResponse?>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCityName: TextView = view.findViewById(R.id.tvCityName)
        val tvTemp: TextView = view.findViewById(R.id.tvTemp)
        val tvWindSpeed: TextView = view.findViewById(R.id.tvWindSpeed)
        val tvCondition: TextView = view.findViewById(R.id.tvCondition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.tvCityName.text = weather?.location?.name
        holder.tvTemp.text = "Temp (F): ${weather?.current?.temp_f}"
        holder.tvWindSpeed.text = "Wind (kph): ${weather?.current?.wind_kph}"
        holder.tvCondition.text = "Condition: ${weather?.current?.condition?.text}"
    }

    override fun getItemCount(): Int = weatherList.size
}
