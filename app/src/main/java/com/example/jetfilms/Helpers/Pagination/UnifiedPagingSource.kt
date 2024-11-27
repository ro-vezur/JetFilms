package com.example.jetfilms.Helpers.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.DTOs.Filters.SortTypes
import com.example.jetfilms.DTOs.UnifiedMedia
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats

class UnifiedPagingSource(
    val getMoviesResponse: suspend (page: Int) -> MoviesResponse,
    val getSerialsResponse: suspend (page: Int) -> SimplifiedSerialsResponse,
    val sortType: SortTypes?,
    val categories: List<MediaFormats>,
    val pagesLimit: Int = Int.MAX_VALUE
) : PagingSource<Int, UnifiedMedia>() {
    override fun getRefreshKey(state: PagingState<Int, UnifiedMedia>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, UnifiedMedia> {

        return try {
            val page = params.key ?: 1

            val moviesResponse = getMoviesResponse(page)
            val serialsResponse = getSerialsResponse(page)
            val unifiedMediaList = mutableListOf<UnifiedMedia>()

            if(categories.contains(MediaFormats.MOVIE)) {
                unifiedMediaList.addAll(moviesResponse.results.map {
                    UnifiedMedia(
                        id = it.id,
                        poster = it.poster.toString(),
                        releaseDate = it.releaseDate,
                        rating = it.rating,
                        popularity = it.popularity,
                        mediaType = MediaFormats.MOVIE
                    )
                }
                )
            }

            if(categories.contains(MediaFormats.SERIES)) {
                unifiedMediaList.addAll(serialsResponse.results.map {
                    UnifiedMedia(
                        id = it.id,
                        poster = it.poster.toString(),
                        releaseDate = it.releaseDate,
                        rating = it.rating,
                        popularity = it.popularity,
                        mediaType = MediaFormats.SERIES
                    )
                }
                )
            }

            when(sortType){
                SortTypes.NEW -> unifiedMediaList.sortByDescending { it.releaseDate }
                SortTypes.POPULAR -> unifiedMediaList.sortByDescending { it.popularity }
                SortTypes.RATING -> unifiedMediaList.sortByDescending { it.rating }
                null ->  unifiedMediaList.sortByDescending { it.popularity }
            }

            if(page > pagesLimit){
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else{
                LoadResult.Page(
                    data = unifiedMediaList,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (unifiedMediaList.isEmpty()) null else page + 1
                )
            }

        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }
}