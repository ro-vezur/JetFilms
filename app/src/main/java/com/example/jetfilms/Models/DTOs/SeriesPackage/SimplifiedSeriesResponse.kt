package com.example.jetfilms.Models.DTOs.SeriesPackage

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedSeriesResponse(
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    val popularity: Float,
    @SerializedName("first_air_date") val releaseDate: String,
    val name: String,
    @SerializedName("vote_average") val rating: Float
)