package com.example.jetfilms.Models.DTOs.UserDTOs

import android.os.Parcelable
import com.example.jetfilms.BASE_MEDIA_CATEGORIES
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Helpers.StringFunctions.splitUsername
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.google.firebase.auth.FirebaseUser
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
    val customProviderUsed: Boolean,
    val recommendedMediaCategories: MutableList<MediaCategories> = mutableListOf(),
    val recommendedMediaGenres: MutableList<MediaGenres> = mutableListOf(),
    val favoriteMediaList: MutableList<FavoriteMedia> = mutableListOf(),
): Parcelable {
    constructor(): this("","","","","",false)

    constructor(firstName: String,lastName: String,email: String,password: String):
            this("",firstName, lastName, email, password,false)

    companion object {
        fun noCustomSignInUser(
            firebaseUser: FirebaseUser,
        ): User {
            val (firstName, lastName) = splitUsername(firebaseUser.displayName.toString())

           return User(
               id = firebaseUser.uid,
               firstName = firstName,
               lastName = lastName,
               email = firebaseUser.email.toString(),
               password = "None",
               customProviderUsed = false,
               recommendedMediaGenres = BASE_MEDIA_GENRES.toMutableList(),
               recommendedMediaCategories = BASE_MEDIA_CATEGORIES.toMutableList(),
            )
        }
    }
}
