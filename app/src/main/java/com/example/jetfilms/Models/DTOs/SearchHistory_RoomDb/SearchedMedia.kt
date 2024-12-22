package com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetfilms.Helpers.DateFormats.DateFormats
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSeriesResponse
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

@Entity(tableName = "searchHistory")
data class SearchedMedia(
    @PrimaryKey()
    val id: String,
    val mediaId: Int,
    val mediaType: String,
    val viewedDateMillis: Long,
) {
    companion object {
        fun fromDetailedMovieResponse(movieData: DetailedMovieResponse): SearchedMedia {
            return SearchedMedia(
                id = "${movieData.id}${MediaCategories.MOVIE.format}",
                mediaId = movieData.id,
                mediaType = MediaCategories.MOVIE.format,
                viewedDateMillis = DateFormats.getCurrentDateMillis()
            )
        }

        fun fromDetailedSeriesResponse(seriesData: DetailedSeriesResponse): SearchedMedia {
            return SearchedMedia(
                id = "${seriesData.id}${MediaCategories.SERIES.format}",
                mediaId = seriesData.id,
                mediaType = MediaCategories.SERIES.format,
                viewedDateMillis = DateFormats.getCurrentDateMillis()
            )
        }
    }
}
