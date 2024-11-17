package com.example.jetfilms.Data_Classes.ParticipantPackage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class DetailedParticipantResponse(
    val id: Int,
    val biography: String,
    @SerializedName("known_for_department") val activity: String,
    val name: String,
    @SerializedName("profile_path") val image: String?
): Parcelable
