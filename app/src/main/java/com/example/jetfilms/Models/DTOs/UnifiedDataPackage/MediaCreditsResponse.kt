package com.example.jetfilms.Models.DTOs.UnifiedDataPackage

import android.os.Parcelable
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParicipantResponses.SimplifiedParticipantResponse
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MediaCreditsResponse (
    val cast: List<SimplifiedParticipantResponse> = listOf()
): Parcelable
