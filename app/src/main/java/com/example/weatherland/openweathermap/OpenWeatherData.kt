package com.example.weatherland.openweathermap

import com.google.gson.annotations.SerializedName


data class WeatherWrapper(
    val weather: Array<WeatherData>,
    val main: MainData,
    val wind : WindData,
    val dt: Int,
    val sys: sysData,
    val visibility: Int
)

class WeatherData(
    val description: String,
    val icon: String,
    @SerializedName("main") val mainDescription: String
)

data class MainData(
    @SerializedName("temp") val temperature: Float,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("temp_min") val temperatureMin: Float,
    @SerializedName("temp_max") val temperatureMax: Float,
    @SerializedName("dt") val date: Int
    )
data class WindData(
    val speed: Float
)
data class sysData(
    val sunrise: Int,
    val sunset: Int
)
