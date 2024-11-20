package com.example.jetfilms.DTOs.MoviePackage

import com.example.jetfilms.DTOs.ParticipantPackage.MovieCreditsResponse

data class MovieDisplay(
    val response: DetailedMovieResponse,
    val movieCast: MovieCreditsResponse,
    val movieImages: ImagesFromTheMovieResponse,
    val similarMovies: MoviesResponse
)
