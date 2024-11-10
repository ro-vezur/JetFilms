package com.example.jetfilms.Data_Classes

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class DetailedMovieDataClass(
    val id: Int,
    val title: String,
    val genres: List<Genre>,
    val overview: String,
    val popularity: Float,
    val runtime: Int,
    val tagline: String,
    val status: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("origin_country") val originCountries:List<String>,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("poster_path") val posterUrl: String?,
): Parcelable

@Parcelize
@Serializable
data class Genre(
    val id: Int,
    val name: String,
): Parcelable

val detailedMoviesDataList = listOf(
    DetailedMovieDataClass(
        id = 1,
        title = "Inception",
        genres = listOf(
            Genre(28, "Action"),
            Genre(878, "Science Fiction")
        ),
        overview = "A thief who infiltrates the subconscious to steal secrets.",
        popularity = 83.5f,
        runtime = 148,
        tagline = "Your mind is the scene of the crime.",
        status = "Released",
        releaseDate = "2010-07-16",
        originCountries = listOf("US"),
        rating = 8.8f,
        posterUrl = "/ljsZTbVsrQSqZgWeep2B1QiDKuh.jpg"
    ),
    DetailedMovieDataClass(
        id = 2,
        title = "The Dark Knight",
        genres = listOf(
            Genre(28, "Action"),
            Genre(80, "Crime"),
            Genre(53, "Thriller")
        ),
        overview = "Batman faces the Joker in a battle for Gotham's soul.",
        popularity = 89.0f,
        runtime = 152,
        tagline = "Why So Serious?",
        status = "Released",
        releaseDate = "2008-07-18",
        originCountries = listOf("US", "UK"),
        rating = 9.0f,
        posterUrl = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
    ),
    DetailedMovieDataClass(
        id = 3,
        title = "Interstellar",
        genres = listOf(
            Genre(12, "Adventure"),
            Genre(878, "Science Fiction"),
            Genre(18, "Drama")
        ),
        overview = "Explorers travel through a wormhole to save humanity.",
        popularity = 92.3f,
        runtime = 169,
        tagline = "Mankind was born on Earth. It was never meant to die here.",
        status = "Released",
        releaseDate = "2014-11-07",
        originCountries = listOf("US", "UK", "Canada"),
        rating = 8.6f,
        posterUrl = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"
    ),
    DetailedMovieDataClass(
        id = 4,
        title = "The Shawshank Redemption",
        genres = listOf(
            Genre(18, "Drama"),
            Genre(80, "Crime")
        ),
        overview = "Two imprisoned men bond over years, finding redemption.",
        popularity = 95.0f,
        runtime = 142,
        tagline = "Fear can hold you prisoner. Hope can set you free.",
        status = "Released",
        releaseDate = "1994-09-23",
        originCountries = listOf("US"),
        rating = 9.3f,
        posterUrl = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg"
    ),
    DetailedMovieDataClass(
        id = 5,
        title = "Parasite",
        genres = listOf(
            Genre(18, "Drama"),
            Genre(53, "Thriller")
        ),
        overview = "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
        popularity = 85.7f,
        runtime = 132,
        tagline = "Act like you own the place.",
        status = "Released",
        releaseDate = "2019-05-30",
        originCountries = listOf("KR"),
        rating = 8.6f,
        posterUrl = "/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"
    ),
    DetailedMovieDataClass(
        id = 6,
        title = "The Godfather",
        genres = listOf(
            Genre(18, "Drama"),
            Genre(80, "Crime")
        ),
        overview = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
        popularity = 98.3f,
        runtime = 175,
        tagline = "An offer you can't refuse.",
        status = "Released",
        releaseDate = "1972-03-24",
        originCountries = listOf("US"),
        rating = 9.2f,
        posterUrl = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"
    ),
    DetailedMovieDataClass(
        id = 7,
        title = "The Lord of the Rings: The Fellowship of the Ring",
        genres = listOf(
            Genre(12, "Adventure"),
            Genre(14, "Fantasy")
        ),
        overview = "A young hobbit, Frodo, is tasked with destroying a powerful ring to save Middle-earth from the dark lord Sauron.",
        popularity = 94.2f,
        runtime = 178,
        tagline = "One ring to rule them all.",
        status = "Released",
        releaseDate = "2001-12-19",
        originCountries = listOf("NZ", "US"),
        rating = 8.8f,
        posterUrl = "/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg"
    ),
    DetailedMovieDataClass(
        id = 8,
        title = "Fight Club",
        genres = listOf(
            Genre(18, "Drama"),
            Genre(53, "Thriller")
        ),
        overview = "An insomniac office worker and a devil-may-care soap maker form an underground fight club.",
        popularity = 87.5f,
        runtime = 139,
        tagline = "Mischief. Mayhem. Soap.",
        status = "Released",
        releaseDate = "1999-10-15",
        originCountries = listOf("US"),
        rating = 8.8f,
        posterUrl = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg"
    )
)