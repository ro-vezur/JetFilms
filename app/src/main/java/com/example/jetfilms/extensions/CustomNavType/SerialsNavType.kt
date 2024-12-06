package com.example.jetfilms.extensions.CustomNavType

import android.os.Bundle
import androidx.navigation.NavType
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import kotlinx.serialization.json.Json

class DetailedSerialNavType : NavType<DetailedSerialResponse>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): DetailedSerialResponse? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): DetailedSerialResponse {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: DetailedSerialResponse) {
        bundle.putParcelable(key, value)
    }
}