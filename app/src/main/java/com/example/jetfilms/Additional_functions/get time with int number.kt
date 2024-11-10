package com.example.jetfilms.Additional_functions

import java.util.concurrent.TimeUnit

fun fromMinutesToHours(minutes: Int): String{
    val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
    val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%2dh %02dm",hours,remainMinutes)
}