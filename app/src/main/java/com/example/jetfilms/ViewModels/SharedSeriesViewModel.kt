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

    suspend fun getSerial(serialId: Int): DetailedSerialResponse = seriesRepository.getSerial(serialId)


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

    suspend fun searchSeries(query: String,page: Int) = seriesRepository.searchSeries(query, page)

    suspend fun getPopularSeries(page: Int = Int.MAX_VALUE) = seriesRepository.getPopularSerials(page)

}
