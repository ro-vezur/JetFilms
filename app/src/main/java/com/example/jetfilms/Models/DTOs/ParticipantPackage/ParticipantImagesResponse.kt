package com.example.jetfilms.Models.DTOs.ParticipantPackage

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantImagesResponse(
    val id: Int = 0,
    val profiles: List<ParticipantImage> = listOf()
)

@Serializable
data class ParticipantImage(
    @SerializedName("file_path") val image: String
)