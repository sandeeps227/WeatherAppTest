package com.example.weatherappjc.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappjc.R
import com.example.weatherappjc.adapters.WeatherAdapter
import com.example.weatherappjc.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WeatherAdapter
    private val viewModel: WeatherViewModel by viewModels()
    private var progressBar: ProgressBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // Set the toolbar as the ActionBar
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.weatherData.observe(this) { weatherList ->
            adapter = WeatherAdapter(weatherList)
            recyclerView.adapter = adapter
            hideProgressBar()
        }

        showProgressBar()
        viewModel.fetchWeatherData(listOf("London", "New York", "Tokyo", "Mumbai", "Sydney"))

        // Call the fetchWeatherData again after 5 seconds
        CoroutineScope(Dispatchers.Main).launch {
            delay(9000)  // 9 seconds delay
            viewModel.fetchWeatherData(listOf("Visakhapatnam", "Sydney", "Tokyo", "London", "New York"))
        }

    }

    private fun showProgressBar() {
        progressBar!!.visibility = View.VISIBLE  // Show ProgressBar
    }

    private fun hideProgressBar() {
        progressBar!!.visibility = View.GONE  // Hide ProgressBar
    }
}
