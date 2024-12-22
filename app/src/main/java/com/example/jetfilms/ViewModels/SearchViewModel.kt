package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesPageResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.Repositories.Api.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _requestSent = MutableStateFlow(false)
    val requestSent = _requestSent.asStateFlow()

    private val _searchSuggestions: MutableStateFlow<List<UnifiedMedia>> = MutableStateFlow(listOf())
    val searchSuggestions = _searchSuggestions.asStateFlow()

    private val _searchedMovies = MutableStateFlow<MoviesPageResponse?>(null)
    val searchedMovies = _searchedMovies.asStateFlow()

    private val _searchedSeries = MutableStateFlow<SeriesPageResponse?>(null)
    val searchedSeries = _searchedSeries.asStateFlow()

    fun setSearchText(text: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _searchText.emit(text)
            }
        }
    }

    fun setIsRequestSent(sent: Boolean){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                _requestSent.emit(sent)
            }
        }
    }

    fun fetchSearchSuggestions(query: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _searchSuggestions.emit(searchRepository.fetchSearchSuggestions(query))
            }
        }
    }

    suspend fun searchMovies(query: String,page: Int) = searchRepository.searchMovies(query, page)

    fun setSearchedMovies(query: String?){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                _searchedMovies.emit(
                    if(query.isNullOrBlank()) null
                    else searchRepository.searchMovies(query.toString(), 1)
                )
            }
        }
    }

    suspend fun searchSeries(query: String,page: Int) = searchRepository.searchSeries(query, page)

    fun setSearchedSerials(query: String?){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                _searchedSeries.emit(
                    if(query.isNullOrBlank()) null
                    else searchRepository.searchSeries(query,1)
                )
            }
        }
    }


}