package com.example.jetfilms.Models.DTOs.UnifiedDataPackage

import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

data class UnifiedMedia(
    val id: Int,
    val name: String,
    val poster: String,
    val releaseDate: String,
    val rating: Float,
    val popularity: Float,
    val mediaType: MediaCategories
)
