package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesResponse
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedSeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
): ViewModel() {

    private val _moreSerialsView: MutableStateFlow<PagingData<SimplifiedSerialObject>> = MutableStateFlow(
        PagingData.empty())
    val moreSerialsView = _moreSerialsView.asStateFlow()

    private val _popularSerials = MutableStateFlow<List<SimplifiedSerialObject>>(listOf())
    val  popularSerials = _popularSerials.asStateFlow()

    init {
        setPopularSerials()
    }

    suspend fun getSerial(serialId: Int): DetailedSerialResponse = seriesRepository.getSerial(serialId)

    suspend fun getSerialSeason(serialId: Int,seasonNumber: Int) = seriesRepository.getSerialSeason(serialId,seasonNumber)


    suspend fun getPopularSerials(page: Int) = seriesRepository.getPopularSerials(page)

    fun setMoreSerialsView(response: suspend (page: Int) -> SeriesResponse, pageLimit: Int = Int.MAX_VALUE){
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

    fun setPopularSerials(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _popularSerials.emit(seriesRepository.getPopularSerials(1).results)
            }
        }
    }

    suspend fun searchSeries(query: String,page: Int) = seriesRepository.searchSeries(query, page)

}
