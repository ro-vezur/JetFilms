package com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import okhttp3.MediaType

@Entity(tableName = "searchHistory")
data class SearchedMedia(
    @PrimaryKey()
    val id: String,
    val mediaId: Int,
    val mediaType: String,
    val viewedDate: String,
)
