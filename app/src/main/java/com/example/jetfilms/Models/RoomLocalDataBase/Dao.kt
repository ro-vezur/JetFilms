package com.example.jetfilms.Models.RoomLocalDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia


@Dao
interface Dao {

    @Query("Select * From searchHistory ORDER BY viewedDateMillis DESC")
    suspend fun getAllSearches(): List<SearchedMedia>

    @Upsert
    suspend fun insertSearchedMedia(searchedMedia: SearchedMedia)

    @Delete
    suspend fun deleteSearchedMedia(searchedMedia: SearchedMedia)

    @Delete
    suspend fun clearSearchHistory(searchHistory: List<SearchedMedia>)

}