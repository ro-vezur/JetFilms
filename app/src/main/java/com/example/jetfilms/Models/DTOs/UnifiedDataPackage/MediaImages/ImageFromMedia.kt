package com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaImages

import kotlinx.serialization.Serializable

@Serializable
data class ImageFromMedia(
    val iso_639_1: String?,
    val file_path: String,
)
