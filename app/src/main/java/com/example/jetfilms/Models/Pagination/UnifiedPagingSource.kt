package com.example.jetfilms.Models.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesPageResponse
import com.example.jetfilms.Models.Enums.SortTypes
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import kotlinx.coroutines.delay

class UnifiedPagingSource(
    val getMoviesResponse: suspend (page: Int) -> MoviesPageResponse,
    val getSerialsResponse: suspend (page: Int) -> SeriesPageResponse,
    val sortType: SortTypes?,
    val categories: List<MediaCategories>,
    val pagesLimit: Int = Int.MAX_VALUE
) : PagingSource<Int, UnifiedMedia>() {
    override fun getRefreshKey(state: PagingState<Int, UnifiedMedia>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, UnifiedMedia> {

        delay(1000)

        return try {
            val page = params.key ?: 1

            val moviesResponse = getMoviesResponse(page)
            val serialsResponse = getSerialsResponse(page)
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
