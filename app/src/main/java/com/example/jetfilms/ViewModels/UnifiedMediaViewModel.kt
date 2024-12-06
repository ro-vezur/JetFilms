package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Models.DTOs.Filters.SortTypes
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Models.Repositories.Api.UnifiedMediaRepository
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UnifiedMediaViewModel @Inject constructor(
    private val unifiedMediaRepository: UnifiedMediaRepository
): ViewModel(){

    private val _selectedMediaCast = MutableStateFlow(UnifiedMediaCreditsResponse())
    val selectedMediaCast = _selectedMediaCast.asStateFlow()

    private val _selectedMediaImages = MutableStateFlow(ImagesFromUnifiedMediaResponse())
    val selectedMediaImages = _selectedMediaImages.asStateFlow()

    private val _selectedMediaTrailers = MutableStateFlow(TrailersResponse())
    val selectedMediaTrailers = _selectedMediaTrailers.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _requestSent = MutableStateFlow(false)
    val requestSent = _requestSent.asStateFlow()

    private val _categories = MutableStateFlow(MediaFormats.entries.toList())
    val categories = _categories.asStateFlow()

    private val _genresFilter = MutableStateFlow(BASE_MEDIA_GENRES)
    val genresFilter = _genresFilter.asStateFlow()

    private val _filteredCountries = MutableStateFlow(getCountryList())
    val filteredCountries = _filteredCountries.asStateFlow()

    private val _selectedSort = MutableStateFlow<SortTypes?>(null)
    val selectedSort = _selectedSort.asStateFlow()

    private val _showFilteredResults = MutableStateFlow(false)
    val showFilteredResults = _showFilteredResults.asStateFlow()

    private val _filteredUnifiedData: MutableStateFlow<PagingData<UnifiedMedia>> = MutableStateFlow(
        PagingData.empty())
    val filteredUnifiedData = _filteredUnifiedData.asStateFlow()

    private val _searchSuggestions: MutableStateFlow<List<UnifiedMedia>> = MutableStateFlow(listOf())
    val searchSuggestions = _searchSuggestions.asStateFlow()

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

    fun showFilteredData(boolean: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _showFilteredResults.emit(boolean)
            }
        }
    }
    fun setSelectedSort(sortType: SortTypes?){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedSort.emit(sortType)
            }
        }
    }

    fun fetchSearchSuggestions(query: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _searchSuggestions.emit(unifiedMediaRepository.fetchSearchSuggestions(query))
            }
        }
    }

    fun setFilteredUnifiedData(
        getMoviesResponse: suspend (page: Int) -> MoviesResponse,
        getSerialsResponse: suspend (page: Int) -> SimplifiedSerialsResponse,
        sortType: SortTypes?,
        categories: List<MediaFormats>,
    ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val paginatedUnifiedData = unifiedMediaRepository.getPaginatedUnifiedData(
                    getMoviesResponse = getMoviesResponse,
                    getSerialsResponse = getSerialsResponse,
                    sortType = sortType,
                    categories = categories,
                )

                paginatedUnifiedData
                    .cachedIn(viewModelScope)
                    .collect{
                        _filteredUnifiedData.emit(it)
                    }
            }
        }
    }



    fun setFilteredGenres(genres:List<MediaGenres>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _genresFilter.emit(genres)
            }
        }
    }

    fun setFilteredCategories(categories:List<MediaFormats>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _categories.emit(categories)
            }
        }
    }

    fun setFilteredCountries(countries:List<String>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _filteredCountries.emit(countries)
            }
        }
    }

    fun setMoviesExtraInformation(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedMediaTrailers.emit(unifiedMediaRepository.getMovieTrailers(movieId))
                _selectedMediaCast.emit(unifiedMediaRepository.getMovieCredits(movieId))
                _selectedMediaImages.emit(unifiedMediaRepository.getMovieImages(movieId))
            }
        }
    }

    fun setSeriesExtraInformation(seriesId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedMediaCast.emit(unifiedMediaRepository.getSeriesCredits(seriesId))
                _selectedMediaImages.emit(unifiedMediaRepository.getSeriesImages(seriesId))
                _selectedMediaTrailers.emit(unifiedMediaRepository.getSeriesTrailers(seriesId))
            }
        }
    }

}