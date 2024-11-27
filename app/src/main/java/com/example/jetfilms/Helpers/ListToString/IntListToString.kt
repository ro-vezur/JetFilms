package com.example.jetfilms.Helpers.ListToString

import com.example.jetfilms.BASE_MEDIA_GENRES
import java.util.Locale

fun IntListToString(list: List<Int>): String{
    return list.mapIndexed { index, number ->
        if (list.lastIndexOf(number) == index) {
            number.toString()
        } else {
            "$number,"
        }
    }.toString().removePrefix("[").removeSuffix("]").replace(" ","")
}
