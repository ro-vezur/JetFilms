package com.example.jetfilms.API

import com.example.jetfilms.APIKEY
import com.example.jetfilms.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse
import com.example.jetfilms.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.ParticipantPackage.DetailedParticipantResponse
import com.example.jetfilms.DTOs.ParticipantPackage.ParticipantFilmography
import com.example.jetfilms.DTOs.ParticipantPackage.ParticipantImagesResponse
import com.example.jetfilms.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.DTOs.SeriesPackage.SerialSeasonResponse
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/popular?$MINIMUM_VOTE_COUNTS")
    suspend fun popularMovies(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = APIKEY,
    ): MoviesResponse

    @GET("movie/top_rated?$MINIMUM_VOTE_COUNTS")
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
    ): UnifiedMediaCreditsResponse

    @GET("movie/{movie_id}/images")
    suspend fun imagesFromMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = APIKEY,
    ): ImagesFromUnifiedMediaResponse

    @GET("movie/{movie_id}/similar?$MINIMUM_VOTE_COUNTS")
    suspend fun similarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): MoviesResponse

    @GET("tv/popular?$MINIMUM_VOTE_COUNTS")
    suspend fun popularSerials(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): SimplifiedSerialsResponse

    @GET("tv/{serial_id}")
    suspend fun serial(
        @Path("serial_id") id: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): DetailedSerialResponse

    @GET("tv/{serial_id}/credits")
    suspend fun serialCredits(
        @Path("serial_id") id: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): UnifiedMediaCreditsResponse

    @GET("tv/{serial_id}/images")
    suspend fun imagesFromSerial(
        @Path("serial_id") id: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): ImagesFromUnifiedMediaResponse

    @GET("tv/{serial_id}/similar?$MINIMUM_VOTE_COUNTS")
    suspend fun similarSerials(
        @Path("serial_id") id: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): SimplifiedSerialsResponse

    @GET("tv/{serial_id}/season/{season_number}")
    suspend fun serialSeason(
        @Path("serial_id") id: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): SerialSeasonResponse

    @GET("person/{person_id}")
    suspend fun participant(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): DetailedParticipantResponse

    @GET("person/{person_id}/movie_credits")
    suspend fun participantFilmography(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): ParticipantFilmography

    @GET("person/{person_id}/images")
    suspend fun participantImages(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): ParticipantImagesResponse

    @GET("search/movie?")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = APIKEY,
    ): MoviesResponse

    @GET("search/tv?")
    suspend fun searchSerials(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = APIKEY
    ): SimplifiedSerialsResponse

    @GET("discover/movie?$MINIMUM_VOTE_COUNTS")
    suspend fun filteredGenresDiscoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") genres: String,
        @Query("with_origin_country") countries: String,
        @Query("api_key") apiKey: String = APIKEY
    ): MoviesResponse

    @GET("discover/tv?$MINIMUM_VOTE_COUNTS")
    suspend fun filteredGenresDiscoverSerials(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") genres: String,
        @Query("with_origin_country") countries: String,
        @Query("api_key") apiKey: String = APIKEY
    ): SimplifiedSerialsResponse

    @GET("discover/tv?$MINIMUM_VOTE_COUNTS")
    suspend fun discoverSerials(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_origin_country") countries: String,
        @Query("api_key") apiKey: String = APIKEY
    ): SimplifiedSerialsResponse

    @GET("discover/movie?$MINIMUM_VOTE_COUNTS")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_origin_country") countries: String,
        @Query("api_key") apiKey: String = APIKEY
    ): MoviesResponse
}