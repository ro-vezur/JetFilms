package com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantImages

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantImagesResponse(
    val id: Int = 0,
    val profiles: List<ParticipantImage> = listOf()
)
