package com.example.jetfilms.Models.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSeriesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesPageResponse
import kotlinx.coroutines.delay

class SerialsPagingSource(
    val getResponse: suspend (page: Int) -> SeriesPageResponse,
    val pageLimit: Int = Int.MAX_VALUE
) : PagingSource<Int, SimplifiedSeriesResponse>() {
    override fun getRefreshKey(state: PagingState<Int, SimplifiedSeriesResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimplifiedSeriesResponse> {

        delay(1000)

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