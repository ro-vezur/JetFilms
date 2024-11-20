package com.example.jetfilms.Helpers.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass

class MoviesPagingSource(
    val getResponse: suspend (page: Int) -> MoviesResponse,
    val pageLimit: Int = Int.MAX_VALUE
) : PagingSource<Int, SimplifiedMovieDataClass>() {
    override fun getRefreshKey(state: PagingState<Int, SimplifiedMovieDataClass>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimplifiedMovieDataClass> {

        return try {
            val page = params.key ?: 1

            val response = getResponse(page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
                )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}