package com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantImages

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantImage(
    @SerializedName("file_path") val image: String
)