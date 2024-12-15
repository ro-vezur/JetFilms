package com.example.jetfilms.Helpers.Date_formats

import android.icu.text.SimpleDateFormat
import android.util.Log
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class DateFormats {
    companion object{
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
    }
}