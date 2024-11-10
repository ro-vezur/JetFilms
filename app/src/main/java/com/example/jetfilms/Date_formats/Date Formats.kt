package com.example.jetfilms.Date_formats

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateFormats {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun year(dateString: String): Int {
        var date = LocalDate.parse(dateString, formatter)
        return  date.year
    }
}