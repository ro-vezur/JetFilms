package com.example.jetfilms.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Models.DTOs.Filters.SortTypes
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Models.Repositories.Api.FilterRepository
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val filterRepository: FilterRepository,
): ViewModel() {

    private val _categoriesFilter = MutableStateFlow(MediaCategories.entries.toList())
    val categoriesFilter = _categoriesFilter.asStateFlow()

    private val _genresFilter = MutableStateFlow(BASE_MEDIA_GENRES)
    val genresFilter = _genresFilter.asStateFlow()

    private val _filteredCountries = MutableStateFlow(getCountryList())
    val filteredCountries = _filteredCountries.asStateFlow()

    private val _filteredYears: MutableStateFlow<Int> = MutableStateFlow(0)
    val filteredYears: StateFlow<Int> = _filteredYears.asStateFlow()

    private val _filterFromYear: MutableStateFlow<String> = MutableStateFlow("1888-1-1")
    val filterFromYear: StateFlow<String> = _filterFromYear.asStateFlow()

    private val _filterToYear: MutableStateFlow<String> = MutableStateFlow("")
    val filterToYear: StateFlow<String> = _filterToYear.asStateFlow()

    private val _selectedSort = MutableStateFlow<SortTypes?>(null)
    val selectedSort = _selectedSort.asStateFlow()

    private val _filteredResults: MutableStateFlow<PagingData<UnifiedMedia>> = MutableStateFlow(
        PagingData.empty())
    val filteredResults = _filteredResults.asStateFlow()

    init {
        setFilteredYearsRange(1888,DateFormats.getCurrentYear())
    }

    fun setFilteredMedia(
        sortType: SortTypes?,
        categories: List<MediaCategories>,
        countries: List<String>,
        genres: List<MediaGenres>,
        year: Int,
        yearRange: Map<String, String>
    ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val paginatedUnifiedData = filterRepository.getPaginatedFilteredMedia(
                    sortType = sortType,
                    categories = categories,
                    countries = countries,
                    genres = genres,
                    year = year,
                    yearRange = yearRange
                )

                paginatedUnifiedData
                    .cachedIn(viewModelScope)
                    .collect{
                        _filteredResults.emit(it)
                    }
            }
        }
    }

    fun setSelectedSort(sortType: SortTypes?) = viewModelScope.launch {
        _selectedSort.emit(sortType)
    }

    fun setFilteredGenres(genres:List<MediaGenres>) = viewModelScope.launch {
        _genresFilter.emit(genres)
    }

    fun setFilteredCategories(categories:List<MediaCategories>) = viewModelScope.launch {
        _categoriesFilter.emit(categories)
    }

    fun setFilteredCountries(countries:List<String>) = viewModelScope.launch {
        _filteredCountries.emit(countries)
    }

    fun setFilteredYears(years: Int) = viewModelScope.launch {
        _filteredYears.emit(years)
    }

    fun setFilteredYearsRange(fromYear: Int, toYear: Int) = viewModelScope.launch {
        Log.d("is 0?",(fromYear == 0 || toYear == 0).toString())
        if(fromYear == 0 || toYear == 0) {
            _filterFromYear.emit("0")
            _filterToYear.emit("0")
        } else {
            _filterFromYear.emit("$fromYear-01-01")
            _filterToYear.emit("$toYear-12-31")
        }
    }

    fun resetFilters() = viewModelScope.launch {
        setSelectedSort(null)
        setFilteredGenres(BASE_MEDIA_GENRES)
        setFilteredCategories(MediaCategories.entries.toList())
        setFilteredCountries(getCountryList())
        setFilteredYears(0)
        setFilteredYearsRange(1888,DateFormats.getCurrentYear())
    }
}