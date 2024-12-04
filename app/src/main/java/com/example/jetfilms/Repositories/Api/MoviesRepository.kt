package com.example.jetfilms.Repositories.Api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.Helpers.ListToString.CountryListToString
import com.example.jetfilms.Helpers.ListToString.IntListToString
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.Helpers.Pagination.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val apiService: ApiInterface){

    fun getPaginatedMovies(getResponse: suspend (page: Int) -> MoviesResponse, pagesLimit: Int = Int.MAX_VALUE) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { MoviesPagingSource(getResponse = {page -> getResponse(page) },pagesLimit) }
        ).flow


    suspend fun getPopularMovies(page: Int) = apiService.popularMovies(page)

    suspend fun getTopRatedMovies() = apiService.topRatedMovies(1)

    suspend fun getMovie(id: Int) = apiService.movie(id)

    suspend fun getSimilarMovies(movieId: Int) = apiService.similarMovies(movieId)

    suspend fun searchMovies(query: String,page: Int) = apiService.searchMovies(query,page)

    suspend fun discoverMovies(page: Int,sortBy: String,genres: List<Int>,countries:List<String>): MoviesResponse {
        return if(genres == BASE_MEDIA_GENRES.map { it.genreId }) {
            apiService.discoverMovies(
                page = page,
                sortBy = sortBy,
                countries = CountryListToString(countries),
            )
        }
        else {
            apiService.filteredGenresDiscoverMovies(
                page = page,
                sortBy = sortBy,
                genres = IntListToString(genres),
                countries = CountryListToString(countries),
            )
        }
    }
}