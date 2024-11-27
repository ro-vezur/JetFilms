package com.example.jetfilms.DTOs.Filters

enum class SortTypes(val title: String, val requestQuery: String) {
    NEW("New","release_date.desc"),
    POPULAR("Popular","popularity.desc"),
    RATING("Rating IMDB","vote_average.desc"),
}