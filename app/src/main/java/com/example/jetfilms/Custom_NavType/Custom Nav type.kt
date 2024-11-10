package com.example.jetfilms.Custom_NavType

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CustomType<T : Parcelable> : NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T? = bundle.getParcelable(key)

    override fun parseValue(value: String): T {
        val type: Type = object : TypeToken<Class<T>>() {}.type
        return Gson().fromJson(value, type)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }
}