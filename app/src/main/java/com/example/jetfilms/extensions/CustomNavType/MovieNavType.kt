package com.example.jetfilms.extensions.CustomNavType

import android.os.Bundle
import androidx.navigation.NavType
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import kotlinx.serialization.json.Json

class MovieNavType : NavType<DetailedMovieResponse>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): DetailedMovieResponse? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): DetailedMovieResponse {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: DetailedMovieResponse) {
        bundle.putParcelable(key, value)
    }
}