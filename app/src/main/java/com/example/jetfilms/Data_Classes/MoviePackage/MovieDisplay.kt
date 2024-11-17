package com.example.jetfilms.Data_Classes.MoviePackage

import com.example.jetfilms.Data_Classes.ParticipantPackage.MovieCreditsResponse

data class MovieDisplay(
    val response: DetailedMovieResponse,
    val movieCast: MovieCreditsResponse,
    val movieImages: ImagesFromTheMovieResponse,
    val similarMovies: MoviesResponse
)
