package com.example.jetfilms.Custom_NavType

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.jetfilms.Data_Classes.DetailedMovieDataClass
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DetailedMovieNavType : NavType<DetailedMovieDataClass>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): DetailedMovieDataClass? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): DetailedMovieDataClass {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: DetailedMovieDataClass) {
        bundle.putParcelable(key, value)
    }


}