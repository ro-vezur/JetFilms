package com.example.jetfilms.Models.DTOs.UserDTOs

import android.os.Parcelable
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
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
    val recommendedMediaFormats: MutableList<MediaCategories> = mutableListOf(),
    val recommendedMediaGenres: MutableList<MediaGenres> = mutableListOf(),
    val favoriteMediaList: MutableList<FavoriteMedia> = mutableListOf(),
): Parcelable {
    constructor(): this("","","","","")

    constructor(firstName: String,lastName: String,email: String,password: String):
            this("",firstName, lastName, email, password)
}
