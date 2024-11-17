package com.example.jetfilms.Repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.PAGESIZE
import com.example.jetfilms.Pagination.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val apiService: ApiInterface){
    suspend fun getPopularMovies() = apiService.popularMovies(1)
    fun getPaginatedPopularMovies(pagesLimit: Int = Int.MAX_VALUE) =
        Pager(
            config = PagingConfig(
                pageSize = PAGESIZE,
            ),
            pagingSourceFactory = { MoviesPagingSource(apiService,"popular",pagesLimit) }
        ).flow


    suspend fun getTopRatedMovies() = apiService.topRatedMovies(1)

    suspend fun getMovie(id: Int) = apiService.movie(id)

    suspend fun getMovieCredits(movieId:Int) = apiService.movieCredits(movieId)

    suspend fun getMovieImages(movieId: Int) = apiService.imagesFromMovie(movieId)

    suspend fun getSimilarMovies(movieId: Int) = apiService.similarMovies(movieId)

    suspend fun getParticipant(participantId: Int) = apiService.participant(participantId)

    suspend fun getParticipantFilmography(participantId: Int) = apiService.participantFilmography(participantId)
}