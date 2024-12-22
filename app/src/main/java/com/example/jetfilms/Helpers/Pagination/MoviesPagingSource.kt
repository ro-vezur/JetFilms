package com.example.jetfilms.Helpers.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieResponse

class MoviesPagingSource(
    val getResponse: suspend (page: Int) -> MoviesPageResponse,
    val pageLimit: Int = Int.MAX_VALUE
) : PagingSource<Int, SimplifiedMovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, SimplifiedMovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimplifiedMovieResponse> {

        return try {
            val page = params.key ?: 1

            val response = getResponse(page)

            if(page > pageLimit){
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else{
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (response.results.isEmpty())null else page + 1
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}