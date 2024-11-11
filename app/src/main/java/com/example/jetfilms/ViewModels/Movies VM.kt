package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.Data_Classes.DetailedMovieDataClass
import com.example.jetfilms.Data_Classes.SimplifiedMovieDataClass
import com.example.jetfilms.Repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {
    private val _popularMovies = MutableStateFlow<List<SimplifiedMovieDataClass>>(listOf())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<List<SimplifiedMovieDataClass>>(listOf())
    val topRatedMovies = _topRatedMovies.asStateFlow()

    private val _moreMoviesView = MutableStateFlow<List<SimplifiedMovieDataClass?>>(listOf())
    val moreMoviesView = _moreMoviesView.asStateFlow()

    private val _selectedMovie = MutableStateFlow<DetailedMovieDataClass?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                moviesRepository.getTopRatedMovies().body()?.let { _topRatedMovies.emit(it.results) }
                getPopularMovies()
            }
        }
    }

    fun selectMovie(movieId:Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                moviesRepository.getMovie(movieId).body()?.let { _selectedMovie.emit(it) }
            }
        }
    }

    fun getPopularMovies(page: Int = 1){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                moviesRepository.getPopularMovies(page).body()?.let { _popularMovies.emit(it.results) }
            }
        }
    }

    fun setMoreMoviesView(movies:List<SimplifiedMovieDataClass>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _moreMoviesView.emit(movies)
            }
        }
    }
}