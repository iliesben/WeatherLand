package com.example.weatherland.utils

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.weatherland.R
import java.text.SimpleDateFormat
import java.util.*


fun getDateDay(s: String): String? {
    try {
        val sdf = SimpleDateFormat("dd MMM")
        val netDate = Date(s.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun getDateTime(s: String): String? {
    try {
        val sdf = SimpleDateFormat("hh:mm a")
        val netDate = Date(s.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun getDateHour(s: Int): String? {
    try {
        val sdf = SimpleDateFormat("HH")
        val netDate = Date(s.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}
