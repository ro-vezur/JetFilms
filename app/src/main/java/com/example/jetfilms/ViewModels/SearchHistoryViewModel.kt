package com.example.jetfilms.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.Repositories.Room.SearchedHistoryRepository
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

    private val _searchedUnifiedMedia: MutableStateFlow<MutableList<UnifiedMedia>> = MutableStateFlow(
        mutableListOf()
    )
    val searchedUnifiedMedia = _searchedUnifiedMedia.asStateFlow()

    private val _searchedHistoryMedia: MutableStateFlow<MutableList<SearchedMedia>> = MutableStateFlow(
        mutableListOf()
    )
    val searchedHistoryMedia = _searchedHistoryMedia.asStateFlow()

    fun addSearchHistoryMedia(unifiedMedia: UnifiedMedia, searchedMedia: SearchedMedia){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val findUnifiedMedia = _searchedUnifiedMedia.value.find { it.id == unifiedMedia.id && it.mediaType == unifiedMedia.mediaType }
                val findSearchedMedia = _searchedHistoryMedia.value.find { it.id == searchedMedia.id && it.mediaType == searchedMedia.mediaType }

                if(findUnifiedMedia == null) {
                    _searchedUnifiedMedia.value.add(unifiedMedia)

                } else{
                    _searchedUnifiedMedia.value[_searchedUnifiedMedia.value.indexOf(findUnifiedMedia)] = findUnifiedMedia
                }

                if(findSearchedMedia == null){
                    _searchedHistoryMedia.value.add(searchedMedia)
                } else{
                    _searchedHistoryMedia.value[_searchedHistoryMedia.value.indexOf(findSearchedMedia)] = searchedMedia
                }
            }
        }
    }

    suspend fun getSearchHistoryMediaIds() = searchedHistoryRepository.getAll()

    suspend fun insertSearchedMedia(searchedMedia: SearchedMedia) = searchedHistoryRepository.insertSearchedMedia(searchedMedia)

    suspend fun deleteSearchedMedia(searchedMedia: SearchedMedia) = searchedHistoryRepository.deleteSearchedMedia(searchedMedia)

    suspend fun clearSearchHistory(searchHistory: List<SearchedMedia>) = searchedHistoryRepository.clearSearchHistory(searchHistory)
}