package com.example.jetfilms.Data_Classes.ParticipantPackage

import com.example.jetfilms.Data_Classes.MoviePackage.SimplifiedMovieDataClass
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantFilmography(
    val cast: List<SimplifiedMovieDataClass> = listOf(),
    val id: Int = 0
)
