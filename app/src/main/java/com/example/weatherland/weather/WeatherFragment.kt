package com.example.weatherland.weather

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherland.App
import com.example.weatherland.R
import com.example.weatherland.openweathermap.WeatherWrapper
import com.example.weatherland.openweathermap.dataToWeather
import com.example.weatherland.utils.getDateDay
import com.example.weatherland.utils.getDateHour
import com.example.weatherland.utils.getDateTime
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class WeatherFragment : Fragment() {

    companion object {
        val EXTRA_CITY_NAME =  "com.example.weatherland.weather.extras.EXTRA_CITY_NAME"

        fun newInstance() = WeatherFragment()
    }

    private val weatherService = App.weatherService
    private lateinit var cityName: String
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var city: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherDescription: TextView
    private lateinit var temperature: TextView
    private lateinit var temperatureMaxMin :TextView
    private lateinit var day: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView
    private lateinit var sunrise: TextView
    private lateinit var sunset: TextView
    private lateinit var weatherConstraint : ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        refreshLayout = view.findViewById(R.id.swiperWeather)
        city = view.findViewById(R.id.cityNameText)
        weatherIcon = view.findViewById(R.id.weatherIcon)
        weatherDescription = view.findViewById(R.id.forecastText)
        temperature = view.findViewById(R.id.temperatureNowText)
        temperatureMaxMin = view.findViewById(R.id.generalTemperaturesText)
        day = view.findViewById(R.id.dayText)
        humidity = view.findViewById(R.id.humidityValue)
        pressure = view.findViewById(R.id.pressureValue)
        sunrise = view.findViewById(R.id.sunriseValue)
        sunset = view.findViewById(R.id.sunsetValue)
        weatherConstraint = view.findViewById(R.id.weatherConstraintLayout)

        refreshLayout.setOnRefreshListener { refreshWeather() }

        return view
    }

    private fun refreshWeather() {
        updateWeatherForCity(cityName)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (this.activity?.intent!!.hasExtra(EXTRA_CITY_NAME)){
            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME))
        }
    }


    private fun updateWeatherForCity(cityName: String) {
        this.cityName = cityName

        if (!refreshLayout.isRefreshing) {
            refreshLayout.isRefreshing = true
        }

        val call = weatherService.getWeather(cityName)
        call.enqueue(object: Callback<WeatherWrapper> {
            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e("GetDataFailure", "could not load city currentObservation", t)
                Toast.makeText(activity,
                    getString(R.string.CouldNotLoadWeather),
                    Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
            }

            override fun onResponse(
                call: Call<WeatherWrapper>,
                response: Response<WeatherWrapper>
            ) {
                refreshLayout.isRefreshing = false
                response?.body()?.let {
                    val weather = dataToWeather(it)
                    updateDate(weather)
                    Log.i("GetDataResponse", "OpenWeatherMap : $weather")
                }
            }
        })
    }


    private fun updateDate(weather: Weather) {
        city.text = cityName.capitalize()
        temperature.text = getString(R.string.weatherTemperatureValue, weather.temperature.toInt())
        temperatureMaxMin.text = getString(R.string.weatherTemperatureMaxMin, weather.temperatureMin.toInt(), weather.temperatureMax.toInt())
        weatherDescription.text = weather.description
        humidity.text = getString(R.string.weatherHumidityValue, weather.humidity)
        pressure.text = getString(R.string.weatherPressureValue, weather.pressure)
        day.text = getDateDay(weather.dt.toString())
        sunset.text =  getDateTime(weather.sunset.toString())
        sunrise.text = getDateTime(weather.sunrise.toString())

        val timer = getDateHour(weather.dt)
        val backgroundImage = getBackground(weather.mainDescription, timer)

        weatherConstraint.background = getBackground(weather.mainDescription, timer)

        Picasso.get()
            .load(weather.iconUrl)
            .placeholder(R.drawable.loader)
            .into(weatherIcon)
    }

    private fun getBackground(description: String, timer: String?): Drawable? {

        var mainImage: String = "weather_clear_"
        var mainTime: String = "afternoon"

        if(description == "Clouds" || description == "Fog") mainImage = "weather_clouds_"
        else if(description == "Rain" || description == "Drizzle" || description == "Thunderstorm" ) mainImage = "weather_rain_"
        else if(description == "Snow") mainImage = "weather_snow_"

        if(timer?.toInt()!! in 6..12) mainTime = "morning"
        else if(timer.toInt() in 13..17) mainTime = "afternoon"
        else if(timer.toInt() in 18..20) mainTime = "sunset"
        else if(timer.toInt() in 21..23) mainTime = "evening"
        else if(timer.toInt() in 0..5 || timer.toInt() == 24) mainTime = "night"


        //else if(timer == "")

        val backgroundImage = "$mainImage$mainTime"

        Log.i("mainDescription" , "$timer")

        return context?.let { ContextCompat.getDrawable(it,resources.getIdentifier( backgroundImage, "drawable", context?.packageName)) }
    }
}