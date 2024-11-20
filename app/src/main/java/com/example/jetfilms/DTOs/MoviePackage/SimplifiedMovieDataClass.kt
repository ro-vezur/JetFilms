package com.example.jetfilms.DTOs.MoviePackage

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedMovieDataClass(
    val id: Int,
    val title: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("poster_path") val posterUrl: String?,
)
