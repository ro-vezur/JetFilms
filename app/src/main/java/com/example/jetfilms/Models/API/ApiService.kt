package com.example.jetfilms.Models.API

import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaCreditsResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParicipantResponses.DetailedParticipantResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantFilmography
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantImages.ParticipantImagesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSeriesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SerialSeasonResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesPageResponse
import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaImages.ImagesFromMediaResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/popular?$MINIMUM_VOTE_COUNTS")
    suspend fun popularMovies(
        @Query("page") page: Int,
    ): MoviesPageResponse

    @GET("movie/top_rated?$MINIMUM_VOTE_COUNTS")
    suspend fun topRatedMovies(
        @Query("page") page: Int,
    ): MoviesPageResponse

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") movieId: Int,
    ): DetailedMovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun movieCredits(
        @Path("movie_id") movieId: Int,
    ): MediaCreditsResponse

    @GET("movie/{movie_id}/images")
    suspend fun imagesFromMovie(
        @Path("movie_id") movieId: Int,
    ): ImagesFromMediaResponse

    @GET("movie/{movie_id}/similar?$MINIMUM_VOTE_COUNTS")
    suspend fun similarMovies(
        @Path("movie_id") movieId: Int,
    ): MoviesPageResponse

    @GET("tv/popular?$MINIMUM_VOTE_COUNTS")
    suspend fun popularSerials(
        @Query("page") page: Int,
    ): SeriesPageResponse

    @GET("tv/{serial_id}")
    suspend fun serial(
        @Path("serial_id") id: Int,
    ): DetailedSeriesResponse

    @GET("tv/{serial_id}/credits")
    suspend fun serialCredits(
        @Path("serial_id") id: Int,
    ): MediaCreditsResponse

    @GET("tv/{serial_id}/images")
    suspend fun imagesFromSerial(
        @Path("serial_id") id: Int,
    ): ImagesFromMediaResponse

    @GET("tv/{serial_id}/similar?$MINIMUM_VOTE_COUNTS")
    suspend fun similarSerials(
        @Path("serial_id") id: Int,
    ): SeriesPageResponse

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
    ): MoviesPageResponse

    @GET("search/tv?")
    suspend fun searchSerials(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SeriesPageResponse

    @GET("discover/tv?$MINIMUM_VOTE_COUNTS")
    suspend fun filteredGenresDiscoverSerials(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") genres: String,
        @Query("with_origin_country") countries: String,
        @Query("first_air_date_year") releaseYear: String,
        @Query("first_air_date.gte") fromYear: String,
        @Query("first_air_date.lte") toYear: String
    ): SeriesPageResponse

    @GET("discover/movie?$MINIMUM_VOTE_COUNTS")
    suspend fun filteredGenresDiscoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") genres: String,
        @Query("with_origin_country") countries: String,
        @Query("primary_release_year") releaseYear: String,
        @Query("primary_release_date.gte") fromYear: String,
        @Query("primary_release_date.lte") toYear: String
    ): MoviesPageResponse

    @GET("discover/tv?$MINIMUM_VOTE_COUNTS")
    suspend fun discoverSerials(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_origin_country") countries: String,
        @Query("first_air_date_year") releaseYear: String,
        @Query("first_air_date.gte") fromYear: String,
        @Query("first_air_date.lte") toYear: String
    ): SeriesPageResponse

    @GET("discover/movie?$MINIMUM_VOTE_COUNTS")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_origin_country") countries: String,
        @Query("primary_release_year") releaseYear: String,
        @Query("primary_release_date.gte") fromYear: String,
        @Query("primary_release_date.lte") toYear: String
    ): MoviesPageResponse

    @GET("movie/{movie_id}/videos")
    suspend fun movieTrailers(
        @Path("movie_id") movieId: Int,
    ): TrailersResponse

    @GET("tv/{series_id}/videos")
    suspend fun seriesTrailers(
        @Path("series_id") seriesId: Int
    ): TrailersResponse
}