package com.example.jetfilms.Models.Repositories.Api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Helpers.ListToString.CountryListToString
import com.example.jetfilms.Helpers.ListToString.IntListToString
import com.example.jetfilms.Models.API.ApiInterface
import com.example.jetfilms.Models.DTOs.Filters.SortTypes
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesResponse
import com.example.jetfilms.Helpers.Pagination.UnifiedPagingSource
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterRepository @Inject constructor(
    private val apiService: ApiInterface
) {

    fun getPaginatedFilteredMedia(
        sortType: SortTypes?,
        categories: List<MediaFormats>,
        countries: List<String>,
        genres: List<MediaGenres>,
        pagesLimit: Int = Int.MAX_VALUE
    ) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
        ),
        pagingSourceFactory = {
            UnifiedPagingSource(
            getMoviesResponse = { page ->
                discoverMovies(
                    page = page,
                    sortBy = sortType?.requestQuery.toString(),
                    genres = genres.map { it.genreId },
                    countries = countries
                )
                                },
            getSerialsResponse = { page ->
                discoverSeries(
                    page = page,
                    sortBy = sortType?.requestQuery.toString(),
                    genres = genres.map { it.genreId },
                    countries = countries
                )
            },
            sortType = sortType,
            categories = categories,
            pagesLimit = pagesLimit
        )
        }
    ).flow

    private suspend fun discoverMovies(page: Int, sortBy: String, genres: List<Int>, countries:List<String>): MoviesResponse {
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

    suspend fun discoverSeries(page: Int, sortBy: String, genres: List<Int>, countries: List<String>): SeriesResponse {
        return if(genres == BASE_MEDIA_GENRES.map { it.genreId }) {
            apiService.discoverSerials(
                page = page,
                sortBy = sortBy,
                countries = CountryListToString(countries),
            )
        }
        else {
            apiService.filteredGenresDiscoverSerials(
                page = page,
                sortBy = sortBy,
                genres = IntListToString(genres),
                countries = CountryListToString(countries),
            )
        }
    }
}