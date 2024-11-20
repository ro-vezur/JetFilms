package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.DTOs.ParticipantPackage.MovieCreditsResponse
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.DTOs.MoviePackage.ImagesFromTheMovieResponse
import com.example.jetfilms.DTOs.ParticipantPackage.ParticipantFilmography
import com.example.jetfilms.DTOs.ParticipantPackage.ParticipantImagesResponse
import com.example.jetfilms.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
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
    private val _popularMovies: MutableStateFlow<List<SimplifiedMovieDataClass>> = MutableStateFlow(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<List<SimplifiedMovieDataClass>>(listOf())
    val topRatedMovies = _topRatedMovies.asStateFlow()

    private val _similarMovies = MutableStateFlow<MoviesResponse?>(null)
    val similarMovies = _similarMovies.asStateFlow()

    private val _moreMoviesView: MutableStateFlow<List<SimplifiedMovieDataClass>> = MutableStateFlow(emptyList())
    val moreMoviesView = _moreMoviesView.asStateFlow()

    private val _moreSerialsView: MutableStateFlow<SimplifiedSerialsResponse?> = MutableStateFlow(null)
    val moreSerialsView = _moreSerialsView.asStateFlow()


    private val _selectedMovie = MutableStateFlow<DetailedMovieResponse?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _selectedMovieCast = MutableStateFlow<MovieCreditsResponse?>(null)
    val selectedMovieCast = _selectedMovieCast.asStateFlow()

    private val _selectedMovieImages = MutableStateFlow(ImagesFromTheMovieResponse())
    val selectedMovieImages = _selectedMovieImages.asStateFlow()


    private val _popularSerials = MutableStateFlow<SimplifiedSerialsResponse?>(null)
    val  popularSerials = _popularSerials.asStateFlow()


    private val _selectedParticipantFilmography = MutableStateFlow<ParticipantFilmography?>(null)
    val selectedParticipantFilmography = _selectedParticipantFilmography.asStateFlow()

    private val _selectedParticipantImages = MutableStateFlow<ParticipantImagesResponse?>(null)
    val selectedParticipantImages = _selectedParticipantImages.asStateFlow()
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _topRatedMovies.emit(moviesRepository.getTopRatedMovies().results)
                if (_topRatedMovies.value.isNotEmpty()) {
                    setSelectedMovie(_topRatedMovies.value[0].id)
                }
                getPopularMovies()
                setPopularSerials()
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

    fun setSelectedMovieAdditions(movieId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedMovieCast.emit(moviesRepository.getMovieCredits(movieId))
                _selectedMovieImages.emit(moviesRepository.getMovieImages(movieId))
                _similarMovies.emit(moviesRepository.getSimilarMovies(movieId))
            }
        }
    }


    fun getPopularMovies(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _popularMovies.emit(moviesRepository.getPopularMovies().results)
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

    fun setMoreSerialsView(serials:SimplifiedSerialsResponse){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _moreSerialsView.emit(serials)
            }
        }
    }

    fun setPopularSerials(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _popularSerials.emit(moviesRepository.getPopularSerials())
            }
        }
    }


    fun setParticipantFilmography(participantId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedParticipantFilmography.emit(moviesRepository.getParticipantFilmography(participantId))
            }
        }
    }

    fun setParticipantImages(participantId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedParticipantImages.emit(moviesRepository.getParticipantImages(participantId))
            }
        }
    }

    suspend fun getMovie(movieId: Int): DetailedMovieResponse? = moviesRepository.getMovie(movieId).body()

    suspend fun getSerial(serialId: Int): DetailedSerialResponse = moviesRepository.getSerial(serialId)

    suspend fun getSerialSeason(serialId: Int,seasonNumber: Int) = moviesRepository.getSerialSeason(serialId,seasonNumber)

    suspend fun getParticipant(participantId: Int) = moviesRepository.getParticipant(participantId)
}