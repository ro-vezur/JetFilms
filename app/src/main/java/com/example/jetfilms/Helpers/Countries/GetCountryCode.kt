package com.example.jetfilms.Helpers.Countries

import java.util.Locale

fun getCountryCode(countryName: String) =
    Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
