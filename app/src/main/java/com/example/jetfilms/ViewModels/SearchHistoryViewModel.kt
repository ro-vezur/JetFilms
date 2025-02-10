package com.example.jetfilms.ViewModels

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import com.example.jetfilms.Models.Repositories.Room.SearchedHistoryRepository
import com.example.jetfilms.Models.Resource
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val searchedHistoryRepository: SearchedHistoryRepository,
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
): ViewModel() {

    private val _searchedMediaData: MutableStateFlow<Resource<List<UnifiedMedia>>> = MutableStateFlow(
        Resource.Loading()
    )
    val searchedMediaData = _searchedMediaData.asStateFlow()

    private val _searchedMediaIds: MutableStateFlow<List<SearchedMedia>> = MutableStateFlow(
        emptyList()
    )

    val searchedMediaIds = _searchedMediaIds.asStateFlow()

    init {
        setSearchedMediaData()
    }

    fun setSearchedMediaData() = viewModelScope.launch {
        searchedHistoryRepository.getAll().collectLatest { result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let { data ->
                        val searchedMediaDataList = mutableListOf<UnifiedMedia>()
                        val searchedMediaIdsList = mutableListOf<SearchedMedia>()

                        data.forEach { searchedMedia ->
                            when(searchedMedia.mediaType) {

                                MediaCategories.MOVIE.format -> {
                                    val movie = moviesRepository.getMovie(searchedMedia.mediaId)
                                    val unifiedMedia = UnifiedMedia.fromDetailedMovieResponse(movie)

                                    searchedMediaIdsList.add(searchedMedia)
                                    searchedMediaDataList.add(unifiedMedia)

                                    _searchedMediaIds.emit(searchedMediaIdsList)
                                    _searchedMediaData.emit(Resource.Success(data = searchedMediaDataList))
                                }

                                MediaCategories.SERIES.format -> {
                                    val series = seriesRepository.getSerial(searchedMedia.mediaId)
                                    val unifiedMedia = UnifiedMedia.fromDetailedSeriesResponse(series)

                                    searchedMediaIdsList.add(searchedMedia)
                                    searchedMediaDataList.add(unifiedMedia)

                                    _searchedMediaIds.emit(searchedMediaIdsList)
                                    _searchedMediaData.emit(Resource.Success(data = searchedMediaDataList))
                                }
                            }
                        }

                        if(data.isEmpty()) {
                            _searchedMediaData.emit(Resource.Success(data = emptyList()))
                        }
                    }
                }
                is Resource.Loading -> {
                    _searchedMediaIds.emit(emptyList())
                    _searchedMediaData.emit(Resource.Loading())
                }
                is Resource.Error -> {
                    _searchedMediaIds.emit(emptyList())
                    _searchedMediaData.emit(Resource.Error(message = result.message.toString()))
                }
            }
        }
    }

    suspend fun getMovie(id: Int) = moviesRepository.getMovie(id)

    suspend fun getSeries(id: Int) = seriesRepository.getSerial(id)

    fun insertSearchedMediaToDb(searchedMedia: SearchedMedia) = viewModelScope.launch {
        searchedHistoryRepository.insertSearchedMedia(searchedMedia)
    }

    suspend fun deleteSearchedMedia(searchedMedia: SearchedMedia) = searchedHistoryRepository.deleteSearchedMedia(searchedMedia)

    suspend fun clearSearchHistory(searchHistory: List<SearchedMedia>) = searchedHistoryRepository.clearSearchHistory(searchHistory)

}