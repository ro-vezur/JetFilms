package com.example.jetfilms.Models.Repositories.Api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.Models.API.ApiInterface
import com.example.jetfilms.Models.DTOs.Filters.SortTypes
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Helpers.DTOsConverters.MovieDataToUnifiedMedia
import com.example.jetfilms.Helpers.DTOsConverters.SeriesDataToUnifiedMedia
import com.example.jetfilms.Helpers.Pagination.UnifiedPagingSource
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnifiedMediaRepository @Inject constructor(
    private val apiService: ApiInterface
) {

    suspend fun fetchSearchSuggestions(query: String): List<UnifiedMedia> {
        val searchMovies = apiService.searchMovies(query,1)
        val searchSeries = apiService.searchSerials(query,1)

        val unifiedMedia = mutableListOf<UnifiedMedia>()

        unifiedMedia.addAll(searchMovies.results.map { MovieDataToUnifiedMedia(it) })
        unifiedMedia.addAll(searchSeries.results.map { SeriesDataToUnifiedMedia(it) })

        return (searchMovies.results.map { MovieDataToUnifiedMedia(it)} + searchSeries.results.map { SeriesDataToUnifiedMedia(it)}).sortedByDescending { it.rating }.take(10)
    }

    fun getPaginatedUnifiedData(
        getMoviesResponse: suspend (page: Int) -> MoviesResponse,
        getSerialsResponse: suspend (page: Int) -> SimplifiedSerialsResponse,
        sortType: SortTypes?,
        categories: List<MediaFormats>,
        pagesLimit: Int = Int.MAX_VALUE
    ) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
        ),
        pagingSourceFactory = {
            UnifiedPagingSource(
            getMoviesResponse = getMoviesResponse,
            getSerialsResponse = getSerialsResponse,
            sortType = sortType,
            categories = categories,
            pagesLimit = pagesLimit
        )
        }
    ).flow

    suspend fun getMovieCredits(movieId:Int) = apiService.movieCredits(movieId)

    suspend fun getMovieImages(movieId: Int) = apiService.imagesFromMovie(movieId)

    suspend fun getSeriesCredits(serialId: Int) = apiService.serialCredits(serialId)

    suspend fun getSeriesImages(serialId: Int) = apiService.imagesFromSerial(serialId)

    suspend fun getMovieTrailers(movieId: Int) = apiService.movieTrailers(movieId)

    suspend fun getSeriesTrailers(seriesId: Int) = apiService.seriesTrailers(seriesId)
}