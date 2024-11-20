package com.example.jetfilms.DTOs.ParticipantPackage

import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantFilmography(
    val cast: List<SimplifiedMovieDataClass> = listOf(),
    val id: Int = 0
)
