package com.example.jetfilms.Repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.Helpers.Pagination.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val apiService: ApiInterface){
    suspend fun getPopularMovies() = apiService.popularMovies(1)
    fun getPaginatedPopularMovies(pagesLimit: Int = Int.MAX_VALUE) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { MoviesPagingSource(getResponse = {page -> apiService.popularMovies(page) },pagesLimit) }
        ).flow


    suspend fun getTopRatedMovies() = apiService.topRatedMovies(1)

    suspend fun getMovie(id: Int) = apiService.movie(id)

    suspend fun getMovieCredits(movieId:Int) = apiService.movieCredits(movieId)

    suspend fun getMovieImages(movieId: Int) = apiService.imagesFromMovie(movieId)

    suspend fun getSimilarMovies(movieId: Int) = apiService.similarMovies(movieId)

    suspend fun getPopularSerials() = apiService.popularSerials(1)

    suspend fun getSerial(serialId: Int) = apiService.serial(serialId)

    suspend fun getSerialSeason(serialId: Int,seasonNumber: Int) = apiService.serialSeason(serialId,seasonNumber)

    suspend fun getParticipant(participantId: Int) = apiService.participant(participantId)

    suspend fun getParticipantFilmography(participantId: Int) = apiService.participantFilmography(participantId)

    suspend fun getParticipantImages(participantId: Int) = apiService.participantImages(participantId)
}