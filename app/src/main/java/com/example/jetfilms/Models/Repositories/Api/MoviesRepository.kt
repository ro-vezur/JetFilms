package com.example.jetfilms.Models.Repositories.Api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.Models.API.ApiInterface
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.Helpers.ListToString.CountryListToString
import com.example.jetfilms.Helpers.ListToString.IntListToString
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.Helpers.Pagination.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val apiService: ApiInterface){

    suspend fun getMovie(id: Int) = apiService.movie(id)

    fun getPaginatedMovies(getResponse: suspend (page: Int) -> MoviesResponse, pagesLimit: Int = Int.MAX_VALUE) =
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