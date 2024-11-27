package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.DTOs.ParticipantPackage.MovieCreditsResponse
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.DTOs.MoviePackage.ImagesFromTheMovieResponse
import com.example.jetfilms.DTOs.ParticipantPackage.ParticipantFilmography
import com.example.jetfilms.DTOs.ParticipantPackage.ParticipantImagesResponse
import com.example.jetfilms.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.DTOs.Filters.SortTypes
import com.example.jetfilms.DTOs.UnifiedMedia
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Repositories.MoviesRepository
import com.example.jetfilms.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats
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

    private val _moreSerialsView: MutableStateFlow<PagingData<SimplifiedSerialObject>> = MutableStateFlow(PagingData.empty())
    val moreSerialsView = _moreSerialsView.asStateFlow()

    private val _selectedMovie = MutableStateFlow<DetailedMovieResponse?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _selectedMovieCast = MutableStateFlow<MovieCreditsResponse?>(null)
    val selectedMovieCast = _selectedMovieCast.asStateFlow()

    private val _selectedMovieImages = MutableStateFlow(ImagesFromTheMovieResponse())
    val selectedMovieImages = _selectedMovieImages.asStateFlow()


    private val _popularSerials = MutableStateFlow<List<SimplifiedSerialObject>>(listOf())
    val  popularSerials = _popularSerials.asStateFlow()


    private val _selectedParticipantFilmography = MutableStateFlow<ParticipantFilmography?>(null)
    val selectedParticipantFilmography = _selectedParticipantFilmography.asStateFlow()

    private val _selectedParticipantImages = MutableStateFlow<ParticipantImagesResponse?>(null)
    val selectedParticipantImages = _selectedParticipantImages.asStateFlow()

    private val _searchedMovies = MutableStateFlow<MoviesResponse?>(null)
    val searchedMovies = _searchedMovies.asStateFlow()

    private val _searchedSerial = MutableStateFlow<SimplifiedSerialsResponse?>(null)
    val searchedSerials = _searchedSerial.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _requestSent = MutableStateFlow(false)
    val requestSent = _requestSent.asStateFlow()

    private val _categories = MutableStateFlow(MediaFormats.entries.toList())
    val categories = _categories.asStateFlow()

    private val _genresFilter = MutableStateFlow(BASE_MEDIA_GENRES)
    val genresFilter = _genresFilter.asStateFlow()

    private val _filteredCountries = MutableStateFlow(getCountryList())
    val filteredCountries = _filteredCountries.asStateFlow()

    private val _selectedSort = MutableStateFlow<SortTypes?>(null)
    val selectedSort = _selectedSort.asStateFlow()

    private val _showFilteredResults = MutableStateFlow(false)
    val showFilteredResults = _showFilteredResults.asStateFlow()

    private val _filteredUnifiedData: MutableStateFlow<PagingData<UnifiedMedia>> = MutableStateFlow(PagingData.empty())
    val filteredUnifiedData = _filteredUnifiedData.asStateFlow()


    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _topRatedMovies.emit(moviesRepository.getTopRatedMovies().results)
                if (_topRatedMovies.value.isNotEmpty()) {
                    setSelectedMovie(_topRatedMovies.value[0].id)
                }
                setPopularMovies()
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

    fun setMoreSerialsView(response: suspend (page: Int) -> SimplifiedSerialsResponse,pageLimit: Int = Int.MAX_VALUE){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val paginatedSerials = moviesRepository.getPaginatedSerials(response,pageLimit)

                paginatedSerials
                    .cachedIn(viewModelScope)
                    .collect{
                        _moreSerialsView.emit(it)
                    }
            }
        }
    }

    fun setPopularSerials(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _popularSerials.emit(moviesRepository.getPopularSerials(1).results)
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

    fun setSearchedSerials(query: String?){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                _searchedSerial.emit(
                   if(query.isNullOrBlank()) null
                   else moviesRepository.searchSerials(query,1)
                )
            }
        }
    }

    fun setSearchText(text: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _searchText.emit(text)
            }
        }
    }

    fun setIsRequestSent(sent: Boolean){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                _requestSent.emit(sent)
            }
        }
    }

    fun showFilteredData(boolean: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _showFilteredResults.emit(boolean)
            }
        }
    }
    fun setSelectedSort(sortType: SortTypes?){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedSort.emit(sortType)
            }
        }
    }

    fun setFilteredUnifiedData(
        getMoviesResponse: suspend (page: Int) -> MoviesResponse,
        getSerialsResponse: suspend (page: Int) -> SimplifiedSerialsResponse,
        sortType: SortTypes?,
        categories: List<MediaFormats>,
    ){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val paginatedUnifiedData = moviesRepository.getPaginatedUnifiedData(
                    getMoviesResponse = getMoviesResponse,
                    getSerialsResponse = getSerialsResponse,
                    sortType = sortType,
                    categories = categories,
                )

                paginatedUnifiedData
                    .cachedIn(viewModelScope)
                    .collect{
                        _filteredUnifiedData.emit(it)
                    }
            }
        }
    }

    fun setFilteredGenres(genres:List<MediaGenres>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _genresFilter.emit(genres)
            }
        }
    }

    fun setFilteredCategories(categories:List<MediaFormats>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _categories.emit(categories)
            }
        }
    }

    fun setFilteredCountries(countries:List<String>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _filteredCountries.emit(countries)
            }
        }
    }

    suspend fun getMovie(movieId: Int): DetailedMovieResponse? = moviesRepository.getMovie(movieId).body()

    suspend fun getSerial(serialId: Int): DetailedSerialResponse = moviesRepository.getSerial(serialId)

    suspend fun getSerialSeason(serialId: Int,seasonNumber: Int) = moviesRepository.getSerialSeason(serialId,seasonNumber)

    suspend fun getParticipant(participantId: Int) = moviesRepository.getParticipant(participantId)

    suspend fun searchMovies(query: String, page: Int) = moviesRepository.searchMovies(query,page)

    suspend fun searchSerials(query: String,page: Int) = moviesRepository.searchSerials(query,page)

    suspend fun discoverMovies(page: Int,sortBy: String,genres: List<Int>,countries:List<String>) = moviesRepository.discoverMovies(
        page = page,
        sortBy = sortBy,
        genres = genres,
        countries = countries,
    )

    suspend fun discoverSerials(page: Int,sortBy: String,genres: List<Int>,countries:List<String>) = moviesRepository.discoverSerials(
        page = page,
        sortBy = sortBy,
        genres = genres,
        countries = countries,
    )

    suspend fun getPopularMovies(page: Int) = moviesRepository.getPopularMovies(page)

    suspend fun getPopularSerials(page: Int) = moviesRepository.getPopularSerials(page)


}