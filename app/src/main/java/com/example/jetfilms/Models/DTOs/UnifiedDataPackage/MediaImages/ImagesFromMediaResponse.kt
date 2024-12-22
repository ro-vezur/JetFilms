package com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaImages

import kotlinx.serialization.Serializable

@Serializable
data class ImagesFromMediaResponse(
    val backdrops: List<ImageFromMedia> = listOf()
)
