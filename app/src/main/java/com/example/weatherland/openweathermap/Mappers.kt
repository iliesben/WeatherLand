package com.example.weatherland.openweathermap

import com.example.weatherland.weather.Weather

fun dataToWeather(weatherWrapper: WeatherWrapper) : Weather {
    val weatherFirst = weatherWrapper.weather.first()
    return Weather(
        description = weatherFirst.description,
        mainDescription = weatherFirst.mainDescription,
        temperature = weatherWrapper.main.temperature,
        humidity = weatherWrapper.main.humidity,
        pressure = weatherWrapper.main.pressure,
        temperatureMax = weatherWrapper.main.temperatureMax,
        temperatureMin = weatherWrapper.main.temperatureMin,
        speed = weatherWrapper.wind.speed,
        dt = weatherWrapper.dt,
        sunrise = weatherWrapper.sys.sunrise,
        sunset= weatherWrapper.sys.sunset,
        visibility = weatherWrapper.visibility,
        iconUrl = "https://openweathermap.org/img/w/${weatherFirst.icon}.png"
    )
}