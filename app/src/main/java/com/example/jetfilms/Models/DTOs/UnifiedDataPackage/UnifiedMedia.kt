package com.example.jetfilms.Models.DTOs.UnifiedDataPackage

import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSeriesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSeriesResponse
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

data class UnifiedMedia(
    val id: Int,
    val title: String,
    val poster: String,
    val releaseDate: String,
    val rating: Float,
    val popularity: Float,
    val mediaCategory: MediaCategories
) {
    companion object {
        fun fromDetailedMovieResponse(movie: DetailedMovieResponse): UnifiedMedia {
            return UnifiedMedia(
                id = movie.id,
                title = movie.title,
                poster = movie.posterUrl.toString(),
                mediaCategory = MediaCategories.MOVIE,
                popularity = movie.popularity,
                rating = movie.rating,
                releaseDate = movie.releaseDate,
            )
        }

        fun fromDetailedSeriesResponse(series: DetailedSeriesResponse): UnifiedMedia {
            return UnifiedMedia(
                id = series.id,
                title = series.title.toString(),
                poster = series.poster.toString(),
                mediaCategory = MediaCategories.SERIES,
                popularity = series.popularity,
                rating = series.rating,
                releaseDate = series.releaseDate
            )
        }

        fun fromSimplifiedMovieResponse(movie: SimplifiedMovieResponse): UnifiedMedia {
            return UnifiedMedia(
                id = movie.id,
                title = movie.title,
                poster = movie.posterUrl.toString(),
                mediaCategory = MediaCategories.MOVIE,
                popularity = movie.popularity,
                rating = movie.rating,
                releaseDate = movie.releaseDate,
            )
        }

        fun fromSimplifiedSeriesResponse(series: SimplifiedSeriesResponse): UnifiedMedia {
            return UnifiedMedia(
                id = series.id,
                title = series.name,
                poster = series.poster.toString(),
                mediaCategory = MediaCategories.SERIES,
                popularity = series.popularity,
                rating = series.rating,
                releaseDate = series.releaseDate
            )
        }
    }
}
