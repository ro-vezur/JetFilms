package com.example.jetfilms.Models.DTOs.SeriesPackage

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesPageResponse(
    val page: Int,
    val results: List<SimplifiedSeriesResponse>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)