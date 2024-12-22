package com.example.jetfilms.Models.DTOs.ParticipantPackage

import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParicipantResponses.DetailedParticipantResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantImages.ParticipantImagesResponse

data class DetailedParticipantDisplay(
    val participantResponse: DetailedParticipantResponse,
    val filmography: ParticipantFilmography,
    val images: ParticipantImagesResponse
)
