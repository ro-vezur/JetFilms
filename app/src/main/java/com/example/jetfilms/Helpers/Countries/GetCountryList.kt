package com.example.jetfilms.Helpers.Countries

import java.util.Locale

fun getCountryList(): List<String> {
    return Locale.getISOCountries().toList().sorted()
}