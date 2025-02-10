package com.example.jetfilms.Models.Repositories.Room

import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.Resource
import com.example.jetfilms.Models.RoomLocalDataBase.Dao
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchedHistoryRepository(private val searchHistoryDao: Dao) {
    fun getAll() = flow {
        emit(Resource.Loading())

        kotlinx.coroutines.delay(200)

        emit(Resource.Success(data = searchHistoryDao.getAllSearches()))
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }

    suspend fun insertSearchedMedia(searchedMedia: SearchedMedia) = searchHistoryDao.insertSearchedMedia(searchedMedia)

    suspend fun deleteSearchedMedia(searchedMedia: SearchedMedia) = searchHistoryDao.deleteSearchedMedia(searchedMedia)

    suspend fun clearSearchHistory(searchHistory: List<SearchedMedia>) = searchHistoryDao.clearSearchHistory(searchHistory)
}