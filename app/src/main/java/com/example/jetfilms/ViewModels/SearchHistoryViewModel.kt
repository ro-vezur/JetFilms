package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Helpers.DTOsConverters.ToSearchedMedia.MovieDataToSearchedMedia
import com.example.jetfilms.Helpers.DTOsConverters.ToSearchedMedia.SeriesDataToSearchedMedia
import com.example.jetfilms.Helpers.DTOsConverters.ToUnifiedMedia.MovieDataToUnifiedMedia
import com.example.jetfilms.Helpers.DTOsConverters.ToUnifiedMedia.SeriesDataToUnifiedMedia
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import com.example.jetfilms.Models.Repositories.Room.SearchedHistoryRepository
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val searchedHistoryRepository: SearchedHistoryRepository,
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
): ViewModel() {

    private val _searchedUnifiedMedia: MutableStateFlow<MutableList<UnifiedMedia>> = MutableStateFlow(
        mutableListOf()
    )
    val searchedUnifiedMedia = _searchedUnifiedMedia.asStateFlow()

    private val _searchedHistoryMedia: MutableStateFlow<MutableList<SearchedMedia>> = MutableStateFlow(
        mutableListOf()
    )
    val searchedHistoryMedia = _searchedHistoryMedia.asStateFlow()

    init {
        viewModelScope.launch {
            getAllSearchHistory().forEach { searchedMedia ->
                if (searchedMedia.mediaType == MediaCategories.MOVIE.format) {
                    addMovieToFlow(searchedMedia.mediaId)
                } else {
                    addSeriesToFlow(searchedMedia.mediaId )
                }
            }
        }
    }

    fun addMovieToFlow(id: Int) = viewModelScope.launch {
        moviesRepository.getMovie(id).let { movie ->
            val unifiedMedia = MovieDataToUnifiedMedia(movie)
            val searchedMedia = MovieDataToSearchedMedia(movie)

            val findUnifiedMedia = _searchedUnifiedMedia.value.find { it.id == unifiedMedia.id && it.mediaCategory == unifiedMedia.mediaCategory }
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

    fun addSeriesToFlow(id: Int) = viewModelScope.launch {
        val series = seriesRepository.getSerial(id)

        val unifiedMedia = SeriesDataToUnifiedMedia(series)
        val searchedMedia = SeriesDataToSearchedMedia(series)

        val findUnifiedMedia = _searchedUnifiedMedia.value.find { it.id == unifiedMedia.id && it.mediaCategory == unifiedMedia.mediaCategory }
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

    suspend fun getMovie(id: Int) = moviesRepository.getMovie(id)

    suspend fun getSeries(id: Int) = seriesRepository.getSerial(id)

    suspend fun getAllSearchHistory() = searchedHistoryRepository.getAll()


    suspend fun insertSearchedMediaToDb(searchedMedia: SearchedMedia) = searchedHistoryRepository.insertSearchedMedia(searchedMedia)

    suspend fun deleteSearchedMedia(searchedMedia: SearchedMedia) = searchedHistoryRepository.deleteSearchedMedia(searchedMedia)

    suspend fun clearSearchHistory(searchHistory: List<SearchedMedia>) = searchedHistoryRepository.clearSearchHistory(searchHistory)


}