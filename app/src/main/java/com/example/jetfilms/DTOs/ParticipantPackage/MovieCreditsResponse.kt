package com.example.jetfilms.DTOs.ParticipantPackage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MovieCreditsResponse (
    val id: Int,
    val cast: List<SimplifiedMovieParticipant>
): Parcelable
@Parcelize
@Serializable
data class SimplifiedMovieParticipant(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @SerializedName("known_for_department") val activity: String,
    val name: String,
    val popularity: Float,
    @SerializedName("profile_path") val image: String,
    val character: String,
): Parcelable
