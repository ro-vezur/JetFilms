package com.example.jetfilms.API

import com.example.jetfilms.APIKEY
import com.example.jetfilms.Data_Classes.DetailedMovieDataClass
import com.example.jetfilms.Data_Classes.SimplifiedMovieDataClass
import com.example.jetfilms.Data_Classes.moviesPageDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/popular?")
    suspend fun popularMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = APIKEY,
    ):Response<moviesPageDataClass>

    @GET("movie/top_rated?")
    suspend fun topRatedMovies(
        @Query("api_key") api_key: String = APIKEY
    ):Response<moviesPageDataClass>

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = APIKEY,

    ):Response<DetailedMovieDataClass>


}