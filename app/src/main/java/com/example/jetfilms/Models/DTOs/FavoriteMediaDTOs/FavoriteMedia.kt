package com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs

import android.os.Parcelable
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class FavoriteMedia(
    val id: String,
    val mediaId: Int,
    val mediaFormat: MediaFormats,
    val addedDateMillis: Long,
): Parcelable {
    constructor(): this("",0,MediaFormats.MOVIE,0)
}
