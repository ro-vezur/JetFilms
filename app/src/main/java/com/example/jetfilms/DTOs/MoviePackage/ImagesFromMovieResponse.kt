package com.example.jetfilms.DTOs.MoviePackage

import kotlinx.serialization.Serializable

@Serializable
data class ImagesFromTheMovieResponse(
    val backdrops: List<ImageFromTheMovie> = listOf()
)

@Serializable
data class ImageFromTheMovie(
    val iso_639_1: String?,
    val file_path: String,
)
