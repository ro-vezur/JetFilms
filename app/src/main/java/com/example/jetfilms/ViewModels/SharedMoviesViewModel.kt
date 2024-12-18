package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SharedMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private val _moreMoviesView: MutableStateFlow<PagingData<SimplifiedMovieDataClass>> = MutableStateFlow(PagingData.empty())
    val moreMoviesView = _moreMoviesView.asStateFlow()

    fun setMoreMoviesView(response: suspend (page: Int) -> MoviesResponse, pageLimit: Int = Int.MAX_VALUE){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val paginatedMovies = moviesRepository.getPaginatedMovies(response,pageLimit)

                paginatedMovies
                    .cachedIn(viewModelScope)
                    .collect {
                        _moreMoviesView.emit(it)
                    }
                }
        }
    }

    suspend fun getMovie(movieId: Int): DetailedMovieResponse = moviesRepository.getMovie(movieId)

    suspend fun searchMovies(query: String,page: Int) = moviesRepository.searchMovies(query, page)

    suspend fun getPopularMovies(page: Int = Int.MAX_VALUE) = moviesRepository.getPopularMovies(page)
}