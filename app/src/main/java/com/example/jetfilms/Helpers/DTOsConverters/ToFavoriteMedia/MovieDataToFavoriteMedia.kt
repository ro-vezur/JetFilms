package com.example.jetfilms.Helpers.DTOsConverters.ToFavoriteMedia

import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats

fun MovieDataToFavoriteMedia(detailedSeriesResponse: DetailedMovieResponse): FavoriteMedia {
    return FavoriteMedia(
        id = "${detailedSeriesResponse.id}${MediaFormats.MOVIE.format}",
        mediaId = detailedSeriesResponse.id,
        mediaFormat = MediaFormats.MOVIE,
        addedDateMillis = DateFormats.getCurrentDateMillis()
    )
}