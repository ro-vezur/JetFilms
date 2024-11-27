package com.example.jetfilms.DTOs.SeriesPackage

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedSerialsResponse(
    val page: Int,
    val results: List<SimplifiedSerialObject>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

@Serializable
data class SimplifiedSerialObject(
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    val popularity: Float,
    @SerializedName("first_air_date") val releaseDate: String,
    val name: String,
    @SerializedName("vote_average") val rating: Float
)