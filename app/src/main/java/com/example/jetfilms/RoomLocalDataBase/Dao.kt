package com.example.jetfilms.RoomLocalDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetfilms.DTOs.SearchHistory_RoomDb.SearchedMedia
import kotlinx.coroutines.flow.Flow


@Dao
interface DataBaseDao {

    @Query("Select * FROM searchHistory")
    suspend fun getAllSearches(): List<SearchedMedia>

    @Upsert
    suspend fun insertSearchedMedia(searchedMedia: SearchedMedia)

    @Delete
    suspend fun deleteSearchedMedia(searchedMedia: SearchedMedia)

    @Delete
    suspend fun clearSearchHistory(searchHistory: List<SearchedMedia>)

}