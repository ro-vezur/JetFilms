package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSeriesResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.Models.Repositories.Api.FilterRepository
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    private val _popularSerials: MutableStateFlow<List<SimplifiedSeriesResponse>> = MutableStateFlow(emptyList())
    val  popularSerials: StateFlow<List<SimplifiedSeriesResponse>> = _popularSerials.asStateFlow()

    private val _popularMovies: MutableStateFlow<List<SimplifiedMovieResponse>> = MutableStateFlow(emptyList())
    val popularMovies: StateFlow<List<SimplifiedMovieResponse>> = _popularMovies.asStateFlow()

    private val _recommendedMedia: MutableStateFlow<List<UnifiedMedia>> = MutableStateFlow(emptyList())
    val recommendedMedia: StateFlow<List<UnifiedMedia>> = _recommendedMedia.asStateFlow()

    private val _recommendedMediaGenres: MutableStateFlow<List<MediaGenres>> = MutableStateFlow(emptyList())

    private val _topRatedMovies = MutableStateFlow<List<SimplifiedMovieResponse>>(emptyList())
    val topRatedMovies: StateFlow<List<SimplifiedMovieResponse>> = _topRatedMovies.asStateFlow()

    private val _selectedMovie: MutableStateFlow<DetailedMovieResponse?> = MutableStateFlow(null)
    val selectedMovie: StateFlow<DetailedMovieResponse?> = _selectedMovie.asStateFlow()

    init {
        viewModelScope.launch{
            setPopularMovies()

            setPopularSerials()

            setTopRatedMovies().invokeOnCompletion {
                if (_topRatedMovies.value.isNotEmpty()) {
                    setSelectedMovie(_topRatedMovies.value.first().id)
                }
            }

            setRecommendedMedia()
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
        val recommendedMediaGenres = user.recommendedMediaGenres.shuffled().take(2)
        val recommendedMedia = filterRepository.getFilteredMedia(
            categories = user.recommendedMediaCategories,
            genres = recommendedMediaGenres,
        )

        _recommendedMedia.emit(recommendedMedia)
        _recommendedMediaGenres.emit(recommendedMediaGenres)
    }

    fun getPaginatedRecommendedMedia() = filterRepository.getPaginatedFilteredMedia(
        categories = user.recommendedMediaCategories,
        genres = _recommendedMediaGenres.value,
    )

}