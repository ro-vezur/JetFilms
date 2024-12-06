package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
): ViewModel() {

    private val _similarSerials = MutableStateFlow<SimplifiedSerialsResponse?>(null)
    val similarSerials = _similarSerials.asStateFlow()

    private val _moreSerialsView: MutableStateFlow<PagingData<SimplifiedSerialObject>> = MutableStateFlow(
        PagingData.empty())
    val moreSerialsView = _moreSerialsView.asStateFlow()

    private val _popularSerials = MutableStateFlow<List<SimplifiedSerialObject>>(listOf())
    val  popularSerials = _popularSerials.asStateFlow()

    private val _searchedSerial = MutableStateFlow<SimplifiedSerialsResponse?>(null)
    val searchedSerials = _searchedSerial.asStateFlow()

    init {
        setPopularSerials()
    }

    suspend fun getSerial(serialId: Int): DetailedSerialResponse = seriesRepository.getSerial(serialId)

    suspend fun getSerialSeason(serialId: Int,seasonNumber: Int) = seriesRepository.getSerialSeason(serialId,seasonNumber)

    suspend fun searchSerials(query: String,page: Int) = seriesRepository.searchSerials(query,page)

    suspend fun getPopularSerials(page: Int) = seriesRepository.getPopularSerials(page)

    suspend fun discoverSerials(page: Int,sortBy: String,genres: List<Int>,countries:List<String>) = seriesRepository.discoverSerials(
        page = page,
        sortBy = sortBy,
        genres = genres,
        countries = countries,
    )

    fun setMoreSerialsView(response: suspend (page: Int) -> SimplifiedSerialsResponse, pageLimit: Int = Int.MAX_VALUE){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val paginatedSerials = seriesRepository.getPaginatedSerials(response,pageLimit)

                paginatedSerials
                    .cachedIn(viewModelScope)
                    .collect{
                        _moreSerialsView.emit(it)
                    }
            }
        }
    }

    fun setSimilarSerials(serialId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _similarSerials.emit(seriesRepository.getSimilarSerials(serialId))
            }
        }
    }

    fun setPopularSerials(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _popularSerials.emit(seriesRepository.getPopularSerials(1).results)
            }
        }
    }

    fun setSearchedSerials(query: String?){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                _searchedSerial.emit(
                    if(query.isNullOrBlank()) null
                    else seriesRepository.searchSerials(query,1)
                )
            }
        }
    }
}
