package com.example.jetfilms.ViewModels.DetailedMediaViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse
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
import javax.inject.Inject

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

    private val _movieCast: MutableStateFlow<UnifiedMediaCreditsResponse?> = MutableStateFlow(null)
    val movieCast: StateFlow<UnifiedMediaCreditsResponse?> = _movieCast.asStateFlow()

    private val _movieImages: MutableStateFlow<ImagesFromUnifiedMediaResponse?> = MutableStateFlow(null)
    val movieImages: StateFlow<ImagesFromUnifiedMediaResponse?> = _movieImages.asStateFlow()

    private val _similarMovies: MutableStateFlow<MoviesResponse?> = MutableStateFlow(null)
    val similarMovies: StateFlow<MoviesResponse?> = _similarMovies.asStateFlow()

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

