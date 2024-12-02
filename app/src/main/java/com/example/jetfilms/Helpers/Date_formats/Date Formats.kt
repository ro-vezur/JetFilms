package com.example.jetfilms.Helpers.Date_formats

import android.icu.text.SimpleDateFormat
import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class DateFormats {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

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
    companion object{
        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            return sdf.format(Date())
        }
    }
}