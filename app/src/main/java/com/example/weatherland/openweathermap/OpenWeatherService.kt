package com.example.weatherland.openweathermap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "18e51876c8d93ab4a0bf1a25bcbb8fcd"

interface OpenWeatherService {

    @GET("data/2.5/weather?units=metric&lang=en")
    fun getWeather(@Query("q") cityName: String,
                   @Query("appid") apiKey: String = API_KEY) : Call<WeatherWrapper>
}