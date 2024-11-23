package com.example.jetfilms.Helpers.Date_formats

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateFormats {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun year(dateString: String): Int {
        return  try {
            val date = LocalDate.parse(dateString, formatter)
             date.year
        }
        catch (e:Exception){
            Log.e("error",e.message.toString())
            0
        }
    }
}