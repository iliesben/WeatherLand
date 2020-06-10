package com.example.weatherland.city

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherland.R
import com.example.weatherland.weather.WeatherActivity
import com.example.weatherland.weather.WeatherFragment

class MainActivity : AppCompatActivity(), CityFragment.CityFragmentListener {

    private lateinit var cityFragment: CityFragment
    private var currentCity: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityFragment = supportFragmentManager.findFragmentById(R.id.cityFragment) as CityFragment
        cityFragment.listener = this

    }

    private fun startWeatherActivity(city: City) {
        val intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra(WeatherFragment.EXTRA_CITY_NAME, city.name)
        startActivity(intent)
    }

    override fun onCitySelected(city: City) {
        currentCity = city
        startWeatherActivity(city)
    }

    override fun onSelectionCleared() {
        cityFragment.selectFirstCity()
    }

    override fun onEmptyCities() {
        TODO("Not yet implemented")
    }
}
