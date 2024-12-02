package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Repositories.Room.SearchedHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val searchedHistoryRepository: SearchedHistoryRepository
): ViewModel() {

    private val _searchedHistory: MutableStateFlow<MutableList<UnifiedMedia>> = MutableStateFlow(mutableListOf())
    val searchedHistory = _searchedHistory.asStateFlow()


    fun setSearchHistoryMedia(searchedHistoryMedia: MutableList<UnifiedMedia>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _searchedHistory.emit(searchedHistoryMedia)
            }
        }
    }

    fun addSearchHistoryMedia(unifiedMedia: UnifiedMedia){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _searchedHistory.value.add(unifiedMedia)
            }
        }
    }

    suspend fun getSearchHistoryMediaIds() = searchedHistoryRepository.getAll()

    suspend fun insertSearchedMedia(searchedMedia: SearchedMedia) = searchedHistoryRepository.insertSearchedMedia(searchedMedia)

    suspend fun deleteSearchedMedia(searchedMedia: SearchedMedia) = searchedHistoryRepository.deleteSearchedMedia(searchedMedia)

    suspend fun clearSearchHistory(searchHistory: List<SearchedMedia>) = searchedHistoryRepository.clearSearchHistory(searchHistory)
}