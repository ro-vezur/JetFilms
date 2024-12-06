package com.example.jetfilms.Models.DTOs.SeriesPackage

import kotlinx.serialization.Serializable

@Serializable
data class SerialSeasonResponse(
    val id: Int,
    val episodes: List<Episode>
)
