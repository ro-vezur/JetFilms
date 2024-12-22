package com.example.jetfilms.Models.DTOs.MoviePackage

import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesPageResponse(
    val page: Int,
    val results: List<SimplifiedMovieResponse>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) {
    companion object {
        fun movieToUnifiedMedia(movie: SimplifiedMovieResponse): UnifiedMedia {
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
    }
}
