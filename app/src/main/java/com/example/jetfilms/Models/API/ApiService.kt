package com.example.jetfilms.Models.API

import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantFilmography
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantImagesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SerialSeasonResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/popular?$MINIMUM_VOTE_COUNTS")
    suspend fun popularMovies(
        @Query("page") page: Int,
    ): MoviesResponse

    @GET("movie/top_rated?$MINIMUM_VOTE_COUNTS")
    suspend fun topRatedMovies(
        @Query("page") page: Int,
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") movieId: Int,
    ): Response<DetailedMovieResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun movieCredits(
        @Path("movie_id") movieId: Int,
    ): UnifiedMediaCreditsResponse

    @GET("movie/{movie_id}/images")
    suspend fun imagesFromMovie(
        @Path("movie_id") movieId: Int,
    ): ImagesFromUnifiedMediaResponse

    @GET("movie/{movie_id}/similar?$MINIMUM_VOTE_COUNTS")
    suspend fun similarMovies(
        @Path("movie_id") movieId: Int,
    ): MoviesResponse

    @GET("tv/popular?$MINIMUM_VOTE_COUNTS")
    suspend fun popularSerials(
        @Query("page") page: Int,
    ): SimplifiedSerialsResponse

    @GET("tv/{serial_id}")
    suspend fun serial(
        @Path("serial_id") id: Int,
    ): DetailedSerialResponse

    @GET("tv/{serial_id}/credits")
    suspend fun serialCredits(
        @Path("serial_id") id: Int,
    ): UnifiedMediaCreditsResponse

    @GET("tv/{serial_id}/images")
    suspend fun imagesFromSerial(
        @Path("serial_id") id: Int,
    ): ImagesFromUnifiedMediaResponse

    @GET("tv/{serial_id}/similar?$MINIMUM_VOTE_COUNTS")
    suspend fun similarSerials(
        @Path("serial_id") id: Int,
    ): SimplifiedSerialsResponse

    @GET("tv/{serial_id}/season/{season_number}")
    suspend fun serialSeason(
        @Path("serial_id") id: Int,
        @Path("season_number") seasonNumber: Int,
    ): SerialSeasonResponse

    @GET("person/{person_id}")
    suspend fun participant(
        @Path("person_id") personId: Int,
    ): DetailedParticipantResponse

    @GET("person/{person_id}/movie_credits")
    suspend fun participantFilmography(
        @Path("person_id") personId: Int,
    ): ParticipantFilmography

    @GET("person/{person_id}/images")
    suspend fun participantImages(
        @Path("person_id") personId: Int,
    ): ParticipantImagesResponse

    @GET("search/movie?")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): MoviesResponse

    @GET("search/tv?")
    suspend fun searchSerials(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SimplifiedSerialsResponse

    @GET("discover/movie?$MINIMUM_VOTE_COUNTS")
    suspend fun filteredGenresDiscoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") genres: String,
        @Query("with_origin_country") countries: String,
    ): MoviesResponse

    @GET("discover/tv?$MINIMUM_VOTE_COUNTS")
    suspend fun filteredGenresDiscoverSerials(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") genres: String,
        @Query("with_origin_country") countries: String,
    ): SimplifiedSerialsResponse

    @GET("discover/tv?$MINIMUM_VOTE_COUNTS")
    suspend fun discoverSerials(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_origin_country") countries: String,
    ): SimplifiedSerialsResponse

    @GET("discover/movie?$MINIMUM_VOTE_COUNTS")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_origin_country") countries: String,
    ): MoviesResponse

    @GET("movie/{movie_id}/videos")
    suspend fun movieTrailers(
        @Path("movie_id") movieId: Int,
    ): TrailersResponse

    @GET("tv/{series_id}/videos")
    suspend fun seriesTrailers(
        @Path("series_id") seriesId: Int
    ): TrailersResponse
}