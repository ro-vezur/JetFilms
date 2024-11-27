package com.example.jetfilms.Helpers.Countries

import java.util.Locale

fun getLanguageCodeByCountry(countryCode: String): String? {
    val locale = Locale.getAvailableLocales().find { it.country.equals(countryCode, ignoreCase = true) }
    return locale?.language
}