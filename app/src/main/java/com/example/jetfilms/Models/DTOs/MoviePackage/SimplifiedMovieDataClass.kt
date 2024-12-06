package com.example.jetfilms.Models.DTOs.MoviePackage

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedMovieDataClass(
    val id: Int,
    val title: String,
    val popularity: Float,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("poster_path") val poster: String?,
)
