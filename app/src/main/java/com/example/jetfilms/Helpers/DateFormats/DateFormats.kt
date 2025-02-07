package com.example.jetfilms.Helpers.DateFormats

import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

object DateFormats {
    private val baseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    fun getCurrentYear(): Int {

        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun getYear(dateString: String): Int {
        return  try {
            val date = LocalDate.parse(dateString, baseFormatter)
            date.year
        }
        catch (e:Exception){
            0
        }
    }

    fun getCurrentDateMillis(): Long {
        return System.currentTimeMillis()
    }

    fun getDateFromMillis(millis: Long): String{
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(millis)

        return formatter.format(date)
    }

    fun getMinutesFromMillis(millis: Long): Long {
        return millis / 60000
    }
}