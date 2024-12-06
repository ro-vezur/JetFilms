package com.example.jetfilms.Models.DTOs.TrailersResponse

import kotlinx.serialization.Serializable

@Serializable
data class TrailersResponse(
    val results: List<TrailerObject> = listOf()
)
