package com.example.jetfilms.Models.DTOs.UserDTOs

import android.os.Parcelable
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class User (
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val recommendedMediaFormats: List<MediaFormats> = listOf(),
    val recommendedMediaGenres: List<MediaGenres> = listOf(),
    val favoriteMovies: List<FavoriteMedia> = listOf(),
    val favoriteSeries: List<FavoriteMedia> = listOf(),
): Parcelable {
    constructor(): this("","","","","")
}