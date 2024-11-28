package com.example.jetfilms.Helpers.ListToString

import com.example.jetfilms.Helpers.Countries.getLanguageCodeByCountry
import java.util.Locale

fun CountryListToString(list: List<String>): String{
    return list.mapIndexed { index, item ->
        if (list.lastIndexOf(item) == index) {
            item
        } else {
            "${item}|"
        }
    }.toString().removePrefix("[").removeSuffix("]").replace(" ","").replace(",","|")
}
