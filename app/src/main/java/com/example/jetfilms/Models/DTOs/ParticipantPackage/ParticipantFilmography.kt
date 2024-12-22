package com.example.jetfilms.Models.DTOs.ParticipantPackage

import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieResponse
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantFilmography(
    val cast: List<SimplifiedMovieResponse> = listOf(),
    val id: Int = 0
)
