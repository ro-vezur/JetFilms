package com.example.jetfilms.View.Screens

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
object StartScreen

@Serializable
object HomeScreen

@Serializable
object ExploreScreen

@Serializable
object TVScreen

@Serializable
object FavoriteScreen

@Serializable
object AccountScreen{}

@Parcelize
@Serializable
data class MoreMoviesScreenRoute(
    val category: String
): Parcelable

@Parcelize
@Serializable
data class MoreSerialsScreenRoute(
    val category: String
): Parcelable

