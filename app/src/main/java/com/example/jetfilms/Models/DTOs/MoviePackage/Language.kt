package com.example.jetfilms.Models.DTOs.MoviePackage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Language(
    val english_name: String,
    val iso_639_1: String,
): Parcelable