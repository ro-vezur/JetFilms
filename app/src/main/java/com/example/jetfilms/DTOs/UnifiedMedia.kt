package com.example.jetfilms.DTOs

import com.example.jetfilms.Screens.Start.Select_type.MediaFormats

data class UnifiedMedia(
    val id: Int,
    val poster: String,
    val releaseDate: String,
    val rating: Float,
    val popularity: Float,
    val mediaType: MediaFormats
)
