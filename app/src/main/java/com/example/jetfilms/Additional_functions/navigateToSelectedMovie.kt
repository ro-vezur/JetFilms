package com.example.jetfilms.Additional_functions

import androidx.navigation.NavController
import com.example.jetfilms.Data_Classes.DetailedMovieDataClass
import com.example.jetfilms.encodes.encodeStringWithSpecialCharacter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavigateToSelectedMovie(navController: NavController,selectedMovie:DetailedMovieDataClass){

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