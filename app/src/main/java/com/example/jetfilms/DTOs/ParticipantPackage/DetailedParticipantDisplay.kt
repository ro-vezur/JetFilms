package com.example.jetfilms.DTOs.ParticipantPackage

data class DetailedParticipantDisplay(
    val participantResponse: DetailedParticipantResponse,
    val filmography: ParticipantFilmography,
    val images: ParticipantImagesResponse
)
