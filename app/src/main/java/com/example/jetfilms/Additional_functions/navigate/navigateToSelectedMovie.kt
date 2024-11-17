package com.example.jetfilms.Additional_functions.navigate

import androidx.navigation.NavController
import com.example.jetfilms.Data_Classes.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.encodes.encodeStringWithSpecialCharacter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun navigateToSelectedMovie(navController: NavController, selectedMovie: DetailedMovieResponse){

    val jsonMovie = Json.encodeToString(
        selectedMovie.copy(
            title = encodeStringWithSpecialCharacter(selectedMovie.title),
            overview = encodeStringWithSpecialCharacter(selectedMovie.overview),
            tagline = encodeStringWithSpecialCharacter(selectedMovie.tagline),
            posterUrl = encodeStringWithSpecialCharacter(selectedMovie.posterUrl.toString()),
            )
    )

    navController.navigate("movie_details/$jsonMovie")

}