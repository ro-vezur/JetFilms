package com.example.jetfilms.Helpers.DTOsConverters

import com.example.jetfilms.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats

fun MovieDataToUnifiedMedia(movie: SimplifiedMovieDataClass): UnifiedMedia {
    return UnifiedMedia(
        id = movie.id,
        name = movie.title,
        poster = movie.poster.toString(),
        mediaType = MediaFormats.MOVIE,
        popularity = movie.popularity,
        rating = movie.rating,
        releaseDate = movie.releaseDate,
    )
}

fun MovieDataToUnifiedMedia(movie: DetailedMovieResponse): UnifiedMedia {
    return UnifiedMedia(
        id = movie.id,
        name = movie.title,
        poster = movie.posterUrl.toString(),
        mediaType = MediaFormats.MOVIE,
        popularity = movie.popularity,
        rating = movie.rating,
        releaseDate = movie.releaseDate,
    )
}