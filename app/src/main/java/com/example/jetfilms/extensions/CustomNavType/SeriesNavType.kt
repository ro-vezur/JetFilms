package com.example.jetfilms.extensions.CustomNavType

import android.os.Bundle
import androidx.navigation.NavType
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSeriesResponse
import kotlinx.serialization.json.Json

class SeriesNavType : NavType<DetailedSeriesResponse>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): DetailedSeriesResponse? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): DetailedSeriesResponse {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: DetailedSeriesResponse) {
        bundle.putParcelable(key, value)
    }
}