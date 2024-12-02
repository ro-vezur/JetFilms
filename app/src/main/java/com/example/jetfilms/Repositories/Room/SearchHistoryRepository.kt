package com.example.jetfilms.Repositories.Room

import com.example.jetfilms.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.RoomLocalDataBase.DataBaseDao

class SearchedHistoryRepository(private val searchHistoryDao: DataBaseDao) {
    suspend fun getAll() = searchHistoryDao.getAllSearches()

    suspend fun insertSearchedMedia(searchedMedia: SearchedMedia) = searchHistoryDao.insertSearchedMedia(searchedMedia)

    suspend fun deleteSearchedMedia(searchedMedia: SearchedMedia) = searchHistoryDao.deleteSearchedMedia(searchedMedia)

    suspend fun clearSearchHistory(searchHistory: List<SearchedMedia>) = searchHistoryDao.clearSearchHistory(searchHistory)
}