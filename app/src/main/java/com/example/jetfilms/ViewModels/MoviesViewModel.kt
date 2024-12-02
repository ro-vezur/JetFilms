package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.Repositories.Api.MoviesRepository
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
    private val _popularMovies: MutableStateFlow<List<SimplifiedMovieDataClass>> = MutableStateFlow(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<List<SimplifiedMovieDataClass>>(listOf())
    val topRatedMovies = _topRatedMovies.asStateFlow()

    private val _similarMovies = MutableStateFlow<MoviesResponse?>(null)
    val similarMovies = _similarMovies.asStateFlow()

    private val _moreMoviesView: MutableStateFlow<PagingData<SimplifiedMovieDataClass>> = MutableStateFlow(PagingData.empty())
    val moreMoviesView = _moreMoviesView.asStateFlow()

    private val _selectedMovie = MutableStateFlow<DetailedMovieResponse?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _searchedMovies = MutableStateFlow<MoviesResponse?>(null)
    val searchedMovies = _searchedMovies.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _topRatedMovies.emit(moviesRepository.getTopRatedMovies().results)
                if (_topRatedMovies.value.isNotEmpty()) {
                    setSelectedMovie(_topRatedMovies.value[0].id)
                }
                setPopularMovies()
            }
        }
    }


    fun setSelectedMovie(movieId:Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedMovie.emit(moviesRepository.getMovie(movieId).body())
            }
        }
    }

    fun setSimilarMovies(movieId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _similarMovies.emit(moviesRepository.getSimilarMovies(movieId))
            }
        }
    }

    fun setPopularMovies(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _popularMovies.emit(moviesRepository.getPopularMovies(1).results)
            }
        }
    }

    fun setMoreMoviesView(response: suspend (page: Int) -> MoviesResponse,pageLimit: Int = Int.MAX_VALUE){
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

    fun setSearchedMovies(query: String?){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                    _searchedMovies.emit(
                        if(query.isNullOrBlank()) null
                        else moviesRepository.searchMovies(query.toString(), 1)
                    )
            }
        }
    }

    suspend fun getMovie(movieId: Int): DetailedMovieResponse? = moviesRepository.getMovie(movieId).body()

    suspend fun searchMovies(query: String, page: Int) = moviesRepository.searchMovies(query,page)


    suspend fun discoverMovies(page: Int,sortBy: String,genres: List<Int>,countries:List<String>) = moviesRepository.discoverMovies(
        page = page,
        sortBy = sortBy,
        genres = genres,
        countries = countries,
    )

    suspend fun getPopularMovies(page: Int) = moviesRepository.getPopularMovies(page)
}