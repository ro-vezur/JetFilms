package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetfilms.Models.DTOs.Filters.SortTypes
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.Models.Repositories.Api.FilterRepository
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel(assistedFactory = HomeViewModel.HomeViewModelFactory::class)
class HomeViewModel @AssistedInject constructor(
    @Assisted val user: User,
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
    private val filterRepository: FilterRepository,
): ViewModel() {

    @AssistedFactory
    interface HomeViewModelFactory {
        fun create(user: User): HomeViewModel
    }

    private val _popularSerials = MutableStateFlow<List<SimplifiedSerialObject>>(emptyList())
    val  popularSerials = _popularSerials.asStateFlow()

    private val _popularMovies: MutableStateFlow<List<SimplifiedMovieDataClass>> = MutableStateFlow(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _recommendedMedia: MutableStateFlow<List<UnifiedMedia>> = MutableStateFlow(emptyList())
    val recommendedMedia: StateFlow<List<UnifiedMedia>> = _recommendedMedia.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<List<SimplifiedMovieDataClass>>(emptyList())
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

    fun setRecommendedMedia() = viewModelScope.launch {
        val recommendedMedia = filterRepository.getFilteredMedia(
            categories = user.recommendedMediaCategories,
            genres = user.recommendedMediaGenres.shuffled().take(1),
        )

        _recommendedMedia.emit(recommendedMedia)
    }

}