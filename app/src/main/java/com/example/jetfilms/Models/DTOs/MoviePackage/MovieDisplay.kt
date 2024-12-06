package com.example.jetfilms.Models.DTOs.MoviePackage

import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse

data class MovieDisplay(
    val response: DetailedMovieResponse,
    val movieCast: UnifiedMediaCreditsResponse,
    val movieImages: ImagesFromUnifiedMediaResponse,
    val similarMovies: MoviesResponse,
    val movieTrailers: TrailersResponse,
)
