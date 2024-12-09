package com.example.jetfilms.Helpers.DTOsConverters

import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats

fun DetailedMovieDataToFavoriteMedia(detailedMovieResponse: DetailedMovieResponse): FavoriteMedia {
    return FavoriteMedia(
        id = "${detailedMovieResponse.id}${MediaFormats.MOVIE.format}",
        mediaId = detailedMovieResponse.id,
        mediaFormat = MediaFormats.MOVIE,
        addedDateMillis = DateFormats.getCurrentDateMillis()
    )
}