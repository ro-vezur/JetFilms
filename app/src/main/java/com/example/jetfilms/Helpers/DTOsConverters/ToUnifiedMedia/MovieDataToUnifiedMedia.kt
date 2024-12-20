package com.example.jetfilms.Helpers.DTOsConverters.ToUnifiedMedia

import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

fun MovieDataToUnifiedMedia(movie: SimplifiedMovieDataClass): UnifiedMedia {
    return UnifiedMedia(
        id = movie.id,
        name = movie.title,
        poster = movie.poster.toString(),
        mediaType = MediaCategories.MOVIE,
        popularity = movie.popularity,
        rating = movie.rating,
        releaseDate = movie.releaseDate,
    )
}

fun MovieDataToUnifiedMedia(movie: DetailedMovieResponse): UnifiedMedia {
    return UnifiedMedia(
        id = movie.id,
        name = movie.title,
        poster = movie.posterUrl,
        mediaType = MediaCategories.MOVIE,
        popularity = movie.popularity,
        rating = movie.rating,
        releaseDate = movie.releaseDate,
    )
}