package com.example.weatherland.weather

data class Weather(val description: String,
                   val mainDescription: String,
                   val temperature: Float,
                   val humidity: Int,
                   val pressure: Int,
                   val temperatureMax: Float,
                   val temperatureMin: Float,
                   val speed: Float,
                   val dt: Int,
                   val sunrise: Int,
                   val sunset: Int,
                   val visibility: Int,
                   val iconUrl: String
)
