package com.example.jetfilms.Models.DTOs.MoviePackage

import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaCreditsResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaImages.ImagesFromMediaResponse

data class MovieDisplay(
    val response: DetailedMovieResponse,
    val movieCast: MediaCreditsResponse,
    val movieImages: ImagesFromMediaResponse,
    val similarMovies: MoviesPageResponse,
    val movieTrailers: TrailersResponse,
)
