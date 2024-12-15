package com.example.jetfilms.Helpers.DTOsConverters.ToSearchedMedia

import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats

fun SeriesDataToSearchedMedia(seriesData: DetailedSerialResponse): SearchedMedia {
    return SearchedMedia(
        id = "${seriesData.id}${MediaFormats.SERIES.format}",
        mediaId = seriesData.id,
        mediaType = MediaFormats.SERIES.format,
        viewedDateMillis = DateFormats.getCurrentDateMillis()
    )
}