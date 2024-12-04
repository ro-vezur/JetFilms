package com.example.jetfilms.DTOs.MoviePackage

import com.example.jetfilms.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse
import com.example.jetfilms.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse

data class MovieDisplay(
    val response: DetailedMovieResponse,
    val movieCast: UnifiedMediaCreditsResponse,
    val movieImages: ImagesFromUnifiedMediaResponse,
    val similarMovies: MoviesResponse,
    val movieTrailers: TrailersResponse,
)
