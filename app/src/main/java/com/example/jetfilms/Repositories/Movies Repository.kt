package com.example.jetfilms.Repositories

import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.Data_Classes.SimplifiedMovieDataClass
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val apiService: ApiInterface){
    suspend fun getPopularMovies(page:Int) = apiService.popularMovies(page)

    suspend fun getTopRatedMovies() = apiService.topRatedMovies()

    suspend fun getMovie(id: Int) = apiService.movie(id)
}