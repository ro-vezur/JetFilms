package com.example.jetfilms.API

import com.example.jetfilms.APIKEY
import com.example.jetfilms.Data_Classes.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Data_Classes.ParticipantPackage.MovieCreditsResponse
import com.example.jetfilms.Data_Classes.MoviePackage.ImagesFromTheMovieResponse
import com.example.jetfilms.Data_Classes.MoviePackage.MoviesResponse
import com.example.jetfilms.Data_Classes.ParticipantPackage.DetailedParticipantResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/popular?")
    suspend fun popularMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = APIKEY,
    ): MoviesResponse

    @GET("movie/top_rated?")
    suspend fun topRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = APIKEY
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = APIKEY,
    ): Response<DetailedMovieResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun movieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = APIKEY,
    ): MovieCreditsResponse

    @GET("movie/{movie_id}/images")
    suspend fun imagesFromMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = APIKEY,
    ): ImagesFromTheMovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun similarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): MoviesResponse

    @GET("person/{person_id}")
    suspend fun participant(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): DetailedParticipantResponse

    @GET("person/{person_id}/movie_credits")
    suspend fun participantFilmography(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = APIKEY
    )
}