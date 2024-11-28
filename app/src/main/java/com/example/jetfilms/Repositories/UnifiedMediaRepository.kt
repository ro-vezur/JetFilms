package com.example.jetfilms.Repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.DTOs.Filters.SortTypes
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.Helpers.Pagination.UnifiedPagingSource
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnifiedMediaRepository @Inject constructor(
    private val apiService: ApiInterface
) {
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
}