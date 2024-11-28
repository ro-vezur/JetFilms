package com.example.jetfilms.DTOs.UnifiedDataPackage

import kotlinx.serialization.Serializable

@Serializable
data class ImagesFromUnifiedMediaResponse(
    val backdrops: List<ImageFromUnifiedData> = listOf()
)

@Serializable
data class ImageFromUnifiedData(
    val iso_639_1: String?,
    val file_path: String,
)
