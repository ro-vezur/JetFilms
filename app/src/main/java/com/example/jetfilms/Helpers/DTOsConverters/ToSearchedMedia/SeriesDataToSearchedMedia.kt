package com.example.jetfilms.Helpers.DTOsConverters.ToSearchedMedia

import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

fun SeriesDataToSearchedMedia(seriesData: DetailedSerialResponse): SearchedMedia {
    return SearchedMedia(
        id = "${seriesData.id}${MediaCategories.SERIES.format}",
        mediaId = seriesData.id,
        mediaType = MediaCategories.SERIES.format,
        viewedDateMillis = DateFormats.getCurrentDateMillis()
    )
}