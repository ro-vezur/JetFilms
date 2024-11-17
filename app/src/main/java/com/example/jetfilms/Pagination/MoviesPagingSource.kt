package com.example.jetfilms.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.Data_Classes.MoviePackage.SimplifiedMovieDataClass

class MoviesPagingSource(
    val apiInterface: ApiInterface,
    val type: String,
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

            when (type) {

                "popular" -> {
                    val response = apiInterface.popularMovies(page = page)

                    val endOfPagination = page >= pageLimit

                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = if (endOfPagination) null else page.plus(1),
                    )
                }

                else -> {
                    val response = apiInterface.topRatedMovies(page = page)
                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = if (response.results.isEmpty()) null else page.plus(1),
                    )
                }
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}