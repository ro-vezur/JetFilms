package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
): ViewModel() {

    private val _popularSerials = MutableStateFlow<List<SimplifiedSerialObject>>(listOf())
    val  popularSerials = _popularSerials.asStateFlow()

    private val _popularMovies: MutableStateFlow<List<SimplifiedMovieDataClass>> = MutableStateFlow(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<List<SimplifiedMovieDataClass>>(listOf())
    val topRatedMovies = _topRatedMovies.asStateFlow()

    private val _selectedMovie = MutableStateFlow<DetailedMovieResponse?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    init {
        viewModelScope.launch{
            setPopularMovies()

            setPopularSerials()

            setTopRatedMovies().invokeOnCompletion {
                if (_topRatedMovies.value.isNotEmpty()) {
                    setSelectedMovie(_topRatedMovies.value.first().id)
                }
            }
        }
    }

    fun setSelectedMovie(movieId:Int) = viewModelScope.launch {
        _selectedMovie.emit(moviesRepository.getMovie(movieId))
    }

    fun setPopularMovies() = viewModelScope.launch {
        _popularMovies.emit(moviesRepository.getPopularMovies(1).results)
    }

    fun setTopRatedMovies() = viewModelScope.launch {
        _topRatedMovies.emit(moviesRepository.getTopRatedMovies().results)
    }

    fun setPopularSerials() =  viewModelScope.launch {
        _popularSerials.emit(seriesRepository.getPopularSerials(1).results)
    }



}