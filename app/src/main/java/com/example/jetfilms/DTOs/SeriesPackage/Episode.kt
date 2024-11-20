package com.example.jetfilms.DTOs.SeriesPackage

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    val id: Int,
    val name: String,
    val episode_number: Int,
    @SerializedName("still_path") val image: String?,
    val runtime: Int
)
