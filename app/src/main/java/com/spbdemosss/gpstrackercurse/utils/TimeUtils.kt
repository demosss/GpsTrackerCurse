package com.spbdemosss.gpstrackercurse.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object TimeUtils {
    private val timeFormatter = SimpleDateFormat("HH:mm:ss:SS")
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm")

    fun getTime(timeInMillis: Long): String {
        val cv = Calendar.getInstance()
        timeFormatter.timeZone = TimeZone.getTimeZone("UTC")
        cv.timeInMillis = timeInMillis
        return timeFormatter.format(cv.time)
    }

    fun getDate(): String {
        val cv = Calendar.getInstance()
        return dateFormatter.format(cv.time)
    }
}