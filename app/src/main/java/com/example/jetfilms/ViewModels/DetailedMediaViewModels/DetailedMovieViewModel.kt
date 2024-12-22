package com.example.jetfilms.ViewModels.DetailedMediaViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaCreditsResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaImages.ImagesFromMediaResponse
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.ParticipantRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailedMovieViewModel.DetailedMovieViewModelFactory::class)
class DetailedMovieViewModel @AssistedInject constructor(
    @Assisted val movieId: Int,
    private val moviesRepository: MoviesRepository,
    private val participantRepository: ParticipantRepository,
): ViewModel() {

    @AssistedFactory
    interface DetailedMovieViewModelFactory {
        fun create(movieId: Int): DetailedMovieViewModel
    }

    private val _movieCast: MutableStateFlow<MediaCreditsResponse?> = MutableStateFlow(null)
    val movieCast: StateFlow<MediaCreditsResponse?> = _movieCast.asStateFlow()

    private val _movieImages: MutableStateFlow<ImagesFromMediaResponse?> = MutableStateFlow(null)
    val movieImages: StateFlow<ImagesFromMediaResponse?> = _movieImages.asStateFlow()

    private val _similarMovies: MutableStateFlow<MoviesPageResponse?> = MutableStateFlow(null)
    val similarMovies: StateFlow<MoviesPageResponse?> = _similarMovies.asStateFlow()

    private val _movieTrailers: MutableStateFlow<TrailersResponse?> = MutableStateFlow(null)
    val movieTrailers: StateFlow<TrailersResponse?> = _movieTrailers.asStateFlow()

    init {
        setMovieCast()
        setMovieImages()
        setSimilarMovies()
        setMovieTrailers()
    }

    private fun setMovieCast() = viewModelScope.launch {
        _movieCast.emit(moviesRepository.getMovieCredits(movieId))
    }

    private fun setMovieImages() = viewModelScope.launch {
        _movieImages.emit(moviesRepository.getMovieImages(movieId))
    }

    private fun setSimilarMovies() = viewModelScope.launch {
        _similarMovies.emit(moviesRepository.getSimilarMovies(movieId))
    }

    private fun setMovieTrailers() = viewModelScope.launch {
        _movieTrailers.emit(moviesRepository.getMovieTrailers(movieId))
    }

    suspend fun getParticipant(participantId: Int) = participantRepository.getParticipant(participantId)

}

