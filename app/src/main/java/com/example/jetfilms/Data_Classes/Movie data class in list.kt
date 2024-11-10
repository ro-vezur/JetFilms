package com.example.jetfilms.Data_Classes

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedMovieDataClass(
    val id: Int,
    val title: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("poster_path") val posterUrl: String?,
)

val simplifiedMoviesDataList = listOf(
    SimplifiedMovieDataClass(
        id = 1,
        title = "Inception",
        rating = 8.8f,
        posterUrl = "/ljsZTbVsrQSqZgWeep2B1QiDKuh.jpg"
    ),
    SimplifiedMovieDataClass(
        id = 2,
        title = "The Dark Knight",
        rating = 9.0f,
        posterUrl = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
    ),
    SimplifiedMovieDataClass(
        id = 3,
        title = "Interstellar",
        rating = 8.6f,
        posterUrl = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"
    ),
    SimplifiedMovieDataClass(
        id = 4,
        title = "The Shawshank Redemption",
        rating = 9.3f,
        posterUrl = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg"
    ),
    SimplifiedMovieDataClass(
        id = 5,
        title = "Parasite",
        rating = 8.6f,
        posterUrl = "/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"
    ),
    SimplifiedMovieDataClass(
        id = 6,
        title = "The Godfather",
        rating = 9.2f,
        posterUrl = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"
    ),
    SimplifiedMovieDataClass(
        id = 7,
        title = "The Lord of the Rings: The Fellowship of the Ring",
        rating = 8.8f,
        posterUrl = "/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg"
    ),
    SimplifiedMovieDataClass(
        id = 8,
        title = "Fight Club",
        rating = 8.8f,
        posterUrl = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg"
    )
)