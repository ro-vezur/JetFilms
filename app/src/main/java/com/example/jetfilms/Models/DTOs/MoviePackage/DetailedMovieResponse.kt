package com.example.jetfilms.Models.DTOs.MoviePackage

import android.os.Parcelable
import com.example.jetfilms.Models.DTOs.Filters.Genre
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class DetailedMovieResponse(
    val id: Int = 1,
    val title: String = "",
    val genres: List<Genre> = listOf(),
    val overview: String = "",
    val popularity: Float = 0f,
    val runtime: Int = 0,
    val tagline: String = "",
    val status: String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("origin_country") val originCountries:List<String> = listOf(),
    @SerializedName("vote_average") val rating: Float = 0f,
    @SerializedName("poster_path") val posterUrl: String? = null,
    @SerializedName("spoken_languages") val languages: List<Language> = listOf(),
): Parcelable

@Parcelize
@Serializable
data class Language(
    val english_name: String,
    val iso_639_1: String,
): Parcelable