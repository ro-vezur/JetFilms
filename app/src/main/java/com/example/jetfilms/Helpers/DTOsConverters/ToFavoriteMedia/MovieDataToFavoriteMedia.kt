package com.example.jetfilms.Helpers.DTOsConverters.ToFavoriteMedia

import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

fun MovieDataToFavoriteMedia(detailedSeriesResponse: DetailedMovieResponse): FavoriteMedia {
    return FavoriteMedia(
        id = "${detailedSeriesResponse.id}${MediaCategories.MOVIE.format}",
        mediaId = detailedSeriesResponse.id,
        mediaFormat = MediaCategories.MOVIE,
        addedDateMillis = DateFormats.getCurrentDateMillis()
    )
}