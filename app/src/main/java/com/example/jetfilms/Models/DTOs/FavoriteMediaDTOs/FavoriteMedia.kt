package com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs

import android.os.Parcelable
import com.example.jetfilms.Helpers.DateFormats.DateFormats
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSeriesResponse
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class FavoriteMedia(
    val id: String,
    val mediaId: Int,
    val mediaFormat: MediaCategories,
    val addedDateMillis: Long,
): Parcelable {
    constructor(): this("",0,MediaCategories.MOVIE,0)

    companion object {
        fun fromDetailedSeriesResponse(detailedSeriesResponse: DetailedSeriesResponse): FavoriteMedia {
            return FavoriteMedia(
                id = "${detailedSeriesResponse.id}${MediaCategories.SERIES.format}",
                mediaId = detailedSeriesResponse.id,
                mediaFormat = MediaCategories.SERIES,
                addedDateMillis = DateFormats.getCurrentDateMillis()
            )
        }

        fun fromDetailedMovieResponse(detailedMovieResponse: DetailedMovieResponse): FavoriteMedia {
            return FavoriteMedia(
                id = "${detailedMovieResponse.id}${MediaCategories.MOVIE.format}",
                mediaId = detailedMovieResponse.id,
                mediaFormat = MediaCategories.MOVIE,
                addedDateMillis = DateFormats.getCurrentDateMillis()
            )
        }
    }
}
