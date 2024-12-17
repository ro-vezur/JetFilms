package com.example.jetfilms.Helpers.DTOsConverters.ToUnifiedMedia

import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

fun SeriesDataToUnifiedMedia(series: SimplifiedSerialObject): UnifiedMedia {
    return UnifiedMedia(
        id = series.id,
        name = series.name,
        poster = series.poster.toString(),
        mediaType = MediaCategories.SERIES,
        popularity = series.popularity,
        rating = series.rating,
        releaseDate = series.releaseDate
    )
}

fun SeriesDataToUnifiedMedia(series: DetailedSerialResponse): UnifiedMedia {
    return UnifiedMedia(
        id = series.id,
        name = series.name,
        poster = series.poster.toString(),
        mediaType = MediaCategories.SERIES,
        popularity = series.popularity,
        rating = series.rating,
        releaseDate = series.releaseDate
    )
}