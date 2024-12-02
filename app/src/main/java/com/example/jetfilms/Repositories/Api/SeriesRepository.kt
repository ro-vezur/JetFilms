package com.example.jetfilms.Repositories.Api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.Helpers.ListToString.CountryListToString
import com.example.jetfilms.Helpers.ListToString.IntListToString
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

    suspend fun searchSerials(query: String,page: Int) = apiService.searchSerials(query,page)

    fun getPaginatedSerials(getResponse: suspend (page: Int) -> SimplifiedSerialsResponse, pagesLimit: Int = Int.MAX_VALUE) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { SerialsPagingSource(getResponse = {page -> getResponse(page) },pagesLimit) }
        ).flow

    suspend fun discoverSerials(page: Int,sortBy: String,genres: List<Int>,countries: List<String>): SimplifiedSerialsResponse {
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