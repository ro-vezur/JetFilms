package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSeriesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesPageResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSeriesResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedMediaViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
): ViewModel() {

    private val _moreMoviesView: MutableStateFlow<PagingData<SimplifiedMovieResponse>> = MutableStateFlow(PagingData.empty())
    val moreMoviesView = _moreMoviesView.asStateFlow()

    private val _moreSeriesView: MutableStateFlow<PagingData<SimplifiedSeriesResponse>> = MutableStateFlow(
        PagingData.empty())
    val moreSeriesView = _moreSeriesView.asStateFlow()

    private val _moreUnifiedView: MutableStateFlow<PagingData<UnifiedMedia>> = MutableStateFlow(
        PagingData.empty()
    )
    val moreUnifiedView: StateFlow<PagingData<UnifiedMedia>> = _moreUnifiedView.asStateFlow()

    fun setMoreMoviesView(
        response: suspend (page: Int) -> MoviesPageResponse,
        pageLimit: Int = Int.MAX_VALUE
    ) = viewModelScope.launch  {
        val paginatedMovies = moviesRepository.getPaginatedMovies(response,pageLimit)

        paginatedMovies
            .cachedIn(viewModelScope)
            .collect {
                _moreMoviesView.emit(it)
            }
    }

    fun setMoreSerialsView(
        response: suspend (page: Int) -> SeriesPageResponse,
        pageLimit: Int = Int.MAX_VALUE
    ) = viewModelScope.launch{
        val paginatedSerials = seriesRepository.getPaginatedSerials(response,pageLimit)

        paginatedSerials
            .cachedIn(viewModelScope)
            .collect{
                _moreSeriesView.emit(it)
            }
    }

    fun setMoreUnifiedView(
        paginatedData: Flow<PagingData<UnifiedMedia>>,
    ) = viewModelScope.launch {
        paginatedData
            .cachedIn(viewModelScope)
            .collect{
                _moreUnifiedView.emit(it)
            }
    }

    suspend fun getMovie(movieId: Int): DetailedMovieResponse = moviesRepository.getMovie(movieId)

    suspend fun searchMovies(query: String,page: Int) = moviesRepository.searchMovies(query, page)

    suspend fun getPopularMovies(page: Int = Int.MAX_VALUE) = moviesRepository.getPopularMovies(page)

    suspend fun getSerial(serialId: Int): DetailedSeriesResponse = seriesRepository.getSerial(serialId)

    suspend fun searchSeries(query: String,page: Int) = seriesRepository.searchSeries(query, page)

    suspend fun getPopularSeries(page: Int = Int.MAX_VALUE) = seriesRepository.getPopularSerials(page)
}