package com.example.jetfilms.Data_Classes.MoviePackage

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val page: Int,

    val results: List<SimplifiedMovieDataClass>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)
