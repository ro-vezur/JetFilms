package com.example.jetfilms.Helpers.navigate

import android.util.Log
import androidx.navigation.NavController
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Helpers.encodes.encodeStringWithSpecialCharacter
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
    if(navController.currentDestination?.route.toString() != "movie_details/{movie}") {
        navController.navigate("movie_details/$jsonMovie")
    }
}