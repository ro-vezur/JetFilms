package com.example.jetfilms.Helpers.DTOsConverters.ToFavoriteMedia

import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

fun SeriesDataToFavoriteMedia(detailedMovieResponse: DetailedSerialResponse): FavoriteMedia {
    return FavoriteMedia(
        id = "${detailedMovieResponse.id}${MediaCategories.SERIES.format}",
        mediaId = detailedMovieResponse.id,
        mediaFormat = MediaCategories.SERIES,
        addedDateMillis = DateFormats.getCurrentDateMillis()
    )
}