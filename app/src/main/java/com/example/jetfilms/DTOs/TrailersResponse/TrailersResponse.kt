package com.example.jetfilms.DTOs.TrailersResponse

import kotlinx.serialization.Serializable

@Serializable
data class TrailersResponse(
    val results: List<TrailerObject> = listOf()
)
