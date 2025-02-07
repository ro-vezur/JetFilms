package com.example.jetfilms.Models.Repositories.Api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.BASE_MEDIA_CATEGORIES
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Helpers.DateFormats.DateFormats
import com.example.jetfilms.Helpers.ListToString.CountryListToString
import com.example.jetfilms.Models.API.ApiInterface
import com.example.jetfilms.Models.DTOs.Filters.SortTypes
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesPageResponse
import com.example.jetfilms.Helpers.Pagination.UnifiedPagingSource
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterRepository @Inject constructor(
    private val apiService: ApiInterface
) {

    suspend fun getFilteredMedia(
        sortType: SortTypes = SortTypes.POPULAR,
        categories: List<MediaCategories> = BASE_MEDIA_CATEGORIES,
        countries: List<String> = getCountryList(),
        genres: List<MediaGenres> = BASE_MEDIA_GENRES,
        year: Int = 0,
        yearRange: Map<String,String> = mapOf(
            "fromYear" to "1888-1-1",
            "toYear" to "${DateFormats.getCurrentYear()}-12-31"
        ),
    ): List<UnifiedMedia> {
        val moviesResponse = discoverMovies(
            page = 1,
            sortBy = sortType?.requestQuery.toString(),
            genres = genres.map { it.genreId },
            countries = countries,
            year = year,
            yearRange = yearRange
        )
        val serialsResponse = discoverSeries(
            page = 1,
            sortBy = sortType.requestQuery,
            genres = genres.map { it.genreId },
            countries = countries,
            year = year,
            yearRange = yearRange
        )

        val unifiedMediaList = mutableListOf<UnifiedMedia>()

        if(categories.contains(MediaCategories.MOVIE)) {
            unifiedMediaList.addAll(moviesResponse.results.map {
                UnifiedMedia.fromSimplifiedMovieResponse(it)
            }
            )
        }

        if(categories.contains(MediaCategories.SERIES)) {
            unifiedMediaList.addAll(serialsResponse.results.map {
                UnifiedMedia.fromSimplifiedSeriesResponse(it)
            }
            )
        }

        return when(sortType){
            SortTypes.NEW -> unifiedMediaList.sortedByDescending { it.releaseDate }
            SortTypes.POPULAR -> unifiedMediaList.sortedByDescending { it.popularity }
            SortTypes.RATING -> unifiedMediaList.sortedByDescending { it.rating }
        }
    }

    fun getPaginatedFilteredMedia(
        sortType: SortTypes?= SortTypes.POPULAR,
        categories: List<MediaCategories> = BASE_MEDIA_CATEGORIES,
        countries: List<String> = getCountryList(),
        genres: List<MediaGenres> = BASE_MEDIA_GENRES,
        year: Int = 0,
        yearRange: Map<String,String> = mapOf(
            "fromYear" to "1888-1-1",
            "toYear" to "${DateFormats.getCurrentYear()}-12-31"
        ),
        pagesLimit: Int = Int.MAX_VALUE
    ) = Pager(
        config = PagingConfig(
            pageSize = 18,
        ),
        pagingSourceFactory = {
            UnifiedPagingSource(
                getMoviesResponse = { page ->
                    discoverMovies(
                        page = page,
                        sortBy = sortType?.requestQuery.toString(),
                        genres = genres.map { it.genreId },
                        countries = countries,
                        year = year,
                        yearRange = yearRange,
                    )
                },
                getSerialsResponse = { page ->
                    discoverSeries(
                        page = page,
                        sortBy = sortType?.requestQuery.toString(),
                        genres = genres.map { it.genreId },
                        countries = countries,
                        year = year,
                        yearRange = yearRange,
                    )
                },
                sortType = sortType,
                categories = categories,
                pagesLimit = pagesLimit
        )
        }
    ).flow

    private suspend fun discoverMovies(
        page: Int,
        sortBy: String,
        genres: List<Int>,
        countries:List<String>,
        year: Int,
        yearRange: Map<String, String>,
    ): MoviesPageResponse {
        return if(genres == BASE_MEDIA_GENRES.map { it.genreId }) {
            apiService.discoverMovies(
                page = page,
                sortBy = sortBy,
                countries = CountryListToString(countries),
                releaseYear = year.toString(),
                fromYear = yearRange["fromYear"].toString(),
                toYear = yearRange["toYear"].toString()
            )
        }
        else {
            apiService.filteredGenresDiscoverMovies(
                page = page,
                sortBy = sortBy,
                genres = genres.joinToString(","),
                countries = CountryListToString(countries),
                releaseYear = year.toString(),
                fromYear = yearRange["fromYear"].toString(),
                toYear = yearRange["toYear"].toString()
            )
        }
    }

    private suspend fun discoverSeries(
        page: Int,
        sortBy: String,
        genres: List<Int>,
        countries: List<String>,
        year: Int,
        yearRange: Map<String, String>,
    ): SeriesPageResponse {
        return if(genres == BASE_MEDIA_GENRES.map { it.genreId }) {
            apiService.discoverSerials(
                page = page,
                sortBy = sortBy,
                countries = CountryListToString(countries),
                releaseYear = year.toString(),
                fromYear = yearRange["fromYear"].toString(),
                toYear = yearRange["toYear"].toString()
            )
        }
        else {
            apiService.filteredGenresDiscoverSerials(
                page = page,
                sortBy = sortBy,
                genres = genres.joinToString(","),
                countries = CountryListToString(countries),
                releaseYear = year.toString(),
                fromYear = yearRange["fromYear"].toString(),
                toYear = yearRange["toYear"].toString()
            )
        }
    }
}