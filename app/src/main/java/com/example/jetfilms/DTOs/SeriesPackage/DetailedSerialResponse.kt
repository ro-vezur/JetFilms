package com.example.jetfilms.DTOs.SeriesPackage

import android.os.Parcelable
import com.example.jetfilms.DTOs.Filters.Genre
import com.example.jetfilms.DTOs.MoviePackage.Language
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class DetailedSerialResponse(
    val id: Int,
    @SerializedName("first_air_date") val releaseDate: String,
    val genres: List<Genre>,
    val overview: String,
    val name: String,
    @SerializedName("number_of_seasons") val seasonsCount: Int,
    val popularity: Float,
    @SerializedName("poster_path") val poster: String?,
  //  @SerializedName("origin_country") val countries: List<String> = listOf(),
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("languages") val languages: List<String> = listOf(),
    @SerializedName("origin_country") val originCountries: List<String> = listOf(),
    val seasons: List<SeasonData>
): Parcelable
