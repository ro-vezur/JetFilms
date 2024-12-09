package com.example.jetfilms.Helpers.Date_formats

import android.icu.text.SimpleDateFormat
import android.util.Log
import com.google.type.DateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class DateFormats {
    companion object{
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        fun getYear(dateString: String): Int {
            return  try {
                val date = LocalDate.parse(dateString, formatter)
                date.year
            }
            catch (e:Exception){
                Log.e("error",e.message.toString())
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

        fun getCurrentDate(): String {
            val utcTime = ZonedDateTime.now(ZoneOffset.UTC)

            val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")
            return utcTime.format(formatter)
        }

        fun convertFullDateToDate(dateString: String): String {
            val inputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

            val date = LocalDateTime.parse(dateString, inputFormatter)
            return date.format(outputFormatter)
        }
    }
}