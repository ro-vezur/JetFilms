package com.example.jetfilms.Models.Repositories.Api

import com.example.jetfilms.Helpers.DTOsConverters.ToUnifiedMedia.MovieDataToUnifiedMedia
import com.example.jetfilms.Helpers.DTOsConverters.ToUnifiedMedia.SeriesDataToUnifiedMedia
import com.example.jetfilms.Models.API.ApiInterface
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val apiService: ApiInterface){
    suspend fun fetchSearchSuggestions(query: String): List<UnifiedMedia> {
        val searchMovies = apiService.searchMovies(query,1)
        val searchSeries = apiService.searchSerials(query,1)

        val unifiedMedia = mutableListOf<UnifiedMedia>()

        unifiedMedia.addAll(searchMovies.results.map { MovieDataToUnifiedMedia(it) })
        unifiedMedia.addAll(searchSeries.results.map { SeriesDataToUnifiedMedia(it) })

        return (searchMovies.results.map { MovieDataToUnifiedMedia(it) } + searchSeries.results.map { SeriesDataToUnifiedMedia(it) }).sortedByDescending { it.popularity }.take(12)
    }

    suspend fun searchMovies(query: String,page: Int) = apiService.searchMovies(query,page)

    suspend fun searchSeries(query: String, page: Int) = apiService.searchSerials(query,page)
}