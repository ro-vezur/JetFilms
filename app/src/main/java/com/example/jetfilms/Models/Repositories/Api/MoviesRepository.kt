package com.example.jetfilms.Models.Repositories.Api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.Models.API.ApiInterface
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.Models.Pagination.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val apiService: ApiInterface){

    suspend fun getMovie(id: Int) = apiService.movie(id)

    fun getPaginatedMovies(getResponse: suspend (page: Int) -> MoviesPageResponse, pagesLimit: Int = Int.MAX_VALUE) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { MoviesPagingSource(getResponse = {page -> getResponse(page) },pagesLimit) }
        ).flow


    suspend fun getPopularMovies(page: Int) = apiService.popularMovies(page)

    suspend fun getTopRatedMovies() = apiService.topRatedMovies(1)

    suspend fun getSimilarMovies(movieId: Int) = apiService.similarMovies(movieId)

    suspend fun getMovieCredits(movieId:Int) = apiService.movieCredits(movieId)

    suspend fun getMovieImages(movieId: Int) = apiService.imagesFromMovie(movieId)

    suspend fun getMovieTrailers(movieId: Int) = apiService.movieTrailers(movieId)

    suspend fun searchMovies(query: String,page: Int) = apiService.searchMovies(query,page)
}