package com.example.jetfilms.Models.Repositories.Api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.Models.API.ApiInterface
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesResponse
import com.example.jetfilms.Helpers.Pagination.SerialsPagingSource
import com.example.jetfilms.PAGE_SIZE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeriesRepository @Inject constructor(private val apiService: ApiInterface) {
    suspend fun getSerial(serialId: Int) = apiService.serial(serialId)

    suspend fun getSimilarSerials(serialId: Int) = apiService.similarSerials(serialId)

    suspend fun getSerialSeason(serialId: Int,seasonNumber: Int) = apiService.serialSeason(serialId,seasonNumber)

    suspend fun getPopularSerials(page: Int) = apiService.popularSerials(page)


    fun getPaginatedSerials(getResponse: suspend (page: Int) -> SeriesResponse, pagesLimit: Int = Int.MAX_VALUE) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { SerialsPagingSource(getResponse = {page -> getResponse(page) },pagesLimit) }
        ).flow

    suspend fun getSeriesCredits(serialId: Int) = apiService.serialCredits(serialId)

    suspend fun getSeriesImages(serialId: Int) = apiService.imagesFromSerial(serialId)

    suspend fun getSeriesTrailers(seriesId: Int) = apiService.seriesTrailers(seriesId)

    suspend fun searchSeries(query: String, page: Int) = apiService.searchSerials(query,page)
}