package com.example.jetfilms.DTOs.MoviePackage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class DetailedMovieResponse(
    val id: Int,
    val title: String,
    val genres: List<Genre>,
    val overview: String,
    val popularity: Float,
    val runtime: Int,
    val tagline: String,
    val status: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("origin_country") val originCountries:List<String>,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("poster_path") val posterUrl: String?,
    @SerializedName("spoken_languages") val languages: List<Language>,
): Parcelable

@Parcelize
@Serializable
data class Genre(
    val id: Int,
    val name: String,
): Parcelable

@Parcelize
@Serializable
data class Language(
    val english_name: String,
    val iso_639_1: String,
): Parcelable