package com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs

import android.os.Parcelable
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class FavoriteMedia(
    val id: Int,
    val mediaFormat: MediaFormats,
    val addedDate: String,
): Parcelable
