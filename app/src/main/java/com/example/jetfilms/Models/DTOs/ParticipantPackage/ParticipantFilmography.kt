package com.example.jetfilms.Models.DTOs.ParticipantPackage

import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieDataClass
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantFilmography(
    val cast: List<SimplifiedMovieDataClass> = listOf(),
    val id: Int = 0
)
