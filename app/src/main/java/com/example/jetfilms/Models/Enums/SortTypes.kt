package com.example.jetfilms.Models.Enums

enum class SortTypes(val title: String, val requestQuery: String) {
    NEW("New","release_date.desc"),
    POPULAR("Popular","popularity.desc"),
    RATING("Rating IMDB","vote_average.desc"),
}