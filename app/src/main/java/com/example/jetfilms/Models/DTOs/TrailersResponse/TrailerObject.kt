package com.example.jetfilms.Models.DTOs.TrailersResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TrailerObject(
    val name: String,
    val key: String,
    val site: String,
    val type: String,
): Parcelable