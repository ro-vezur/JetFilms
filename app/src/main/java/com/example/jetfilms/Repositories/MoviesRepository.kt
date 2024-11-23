package com.example.jetfilms.Repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.Helpers.Pagination.MoviesPagingSource
import com.example.jetfilms.Helpers.Pagination.SerialsPagingSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val apiService: ApiInterface){
    suspend fun getPopularMovies(page: Int) = apiService.popularMovies(page)
    fun getPaginatedMovies(getResponse: suspend (page: Int) -> MoviesResponse, pagesLimit: Int = Int.MAX_VALUE) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { MoviesPagingSource(getResponse = {page -> getResponse(page) },pagesLimit) }
        ).flow

    fun getPaginatedSerials(getResponse: suspend (page: Int) -> SimplifiedSerialsResponse, pagesLimit: Int = Int.MAX_VALUE) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { SerialsPagingSource(getResponse = {page -> getResponse(page) },pagesLimit) }
        ).flow

    suspend fun getTopRatedMovies() = apiService.topRatedMovies(1)

    suspend fun getMovie(id: Int) = apiService.movie(id)

    suspend fun getMovieCredits(movieId:Int) = apiService.movieCredits(movieId)

    suspend fun getMovieImages(movieId: Int) = apiService.imagesFromMovie(movieId)

    suspend fun getSimilarMovies(movieId: Int) = apiService.similarMovies(movieId)

    suspend fun getPopularSerials(page: Int) = apiService.popularSerials(page)

    suspend fun getSerial(serialId: Int) = apiService.serial(serialId)

    suspend fun getSerialSeason(serialId: Int,seasonNumber: Int) = apiService.serialSeason(serialId,seasonNumber)

    suspend fun getParticipant(participantId: Int) = apiService.participant(participantId)

    suspend fun getParticipantFilmography(participantId: Int) = apiService.participantFilmography(participantId)

    suspend fun getParticipantImages(participantId: Int) = apiService.participantImages(participantId)

    suspend fun searchMovies(query: String, page: Int) = apiService.searchMovies(query,page)

    suspend fun searchSerials(query: String,page: Int) = apiService.searchSerials(query,page)
}