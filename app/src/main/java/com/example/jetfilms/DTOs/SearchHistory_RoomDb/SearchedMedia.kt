package com.example.jetfilms.DTOs.SearchHistory_RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.MediaType

@Entity(tableName = "searchHistory")
data class SearchedMedia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mediaId: Int,
    val mediaType: String,
    val viewedDate: String,
)
