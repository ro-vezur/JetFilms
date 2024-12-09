package com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchHistory")
data class SearchedMedia(
    @PrimaryKey()
    val id: String,
    val mediaId: Int,
    val mediaType: String,
    val viewedDateMillis: Long,
)
