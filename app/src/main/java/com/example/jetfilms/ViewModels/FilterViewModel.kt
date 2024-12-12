package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Models.DTOs.Filters.SortTypes
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Models.Repositories.Api.FilterRepository
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
class FilterViewModel @Inject constructor(
    private val filterRepository: FilterRepository,
): ViewModel() {

    private val _categories = MutableStateFlow(MediaFormats.entries.toList())
    val categories = _categories.asStateFlow()

    private val _genresFilter = MutableStateFlow(BASE_MEDIA_GENRES)
    val genresFilter = _genresFilter.asStateFlow()

    private val _filteredCountries = MutableStateFlow(getCountryList())
    val filteredCountries = _filteredCountries.asStateFlow()

    private val _selectedSort = MutableStateFlow<SortTypes?>(null)
    val selectedSort = _selectedSort.asStateFlow()

    private val _filteredUnifiedData: MutableStateFlow<PagingData<UnifiedMedia>> = MutableStateFlow(
        PagingData.empty())
    val filteredUnifiedData = _filteredUnifiedData.asStateFlow()

    fun setSelectedSort(sortType: SortTypes?) = viewModelScope.launch {
        _selectedSort.emit(sortType)
    }

    fun setFilteredMedia(
        sortType: SortTypes?,
        categories: List<MediaFormats>,
        countries: List<String>,
        genres: List<MediaGenres>,
    ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val paginatedUnifiedData = filterRepository.getPaginatedFilteredMedia(
                    sortType = sortType,
                    categories = categories,
                    countries = countries,
                    genres = genres,
                )

                paginatedUnifiedData
                    .cachedIn(viewModelScope)
                    .collect{
                        _filteredUnifiedData.emit(it)
                    }
            }
        }
    }

    fun setFilteredGenres(genres:List<MediaGenres>) = viewModelScope.launch {
        _genresFilter.emit(genres)
    }

    fun setFilteredCategories(categories:List<MediaFormats>) = viewModelScope.launch {
        _categories.emit(categories)
    }

    fun setFilteredCountries(countries:List<String>) = viewModelScope.launch {
        _filteredCountries.emit(countries)
    }
}