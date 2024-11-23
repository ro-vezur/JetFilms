package com.example.jetfilms

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetfilms.Helpers.navigate.navigateToSelectedMovie
import com.example.jetfilms.Helpers.navigate.navigateToSelectedParticipant
import com.example.jetfilms.Components.Bottom_Navigation_Bar.BottomNavBar
import com.example.jetfilms.CustomNavType.DetailedMovieNavType
import com.example.jetfilms.CustomNavType.DetailedSerialNavType
import com.example.jetfilms.CustomNavType.ParticipantNavType
import com.example.jetfilms.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.DTOs.MoviePackage.MovieDisplay
import com.example.jetfilms.DTOs.ParticipantPackage.DetailedParticipantResponse
import com.example.jetfilms.DTOs.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Network.ConnectionState
import com.example.jetfilms.Network.connectivityState
import com.example.jetfilms.Screens.Home.HomeScreen
import com.example.jetfilms.Screens.Home.MoreMoviesScreen
import com.example.jetfilms.Screens.Home.MoreSerialsScreen
import com.example.jetfilms.Screens.MoreMoviesScreenRoute
import com.example.jetfilms.Screens.MoreSerialsScreenRoute
import com.example.jetfilms.Screens.MovieDetailsPackage.MovieDetailsScreen
import com.example.jetfilms.Screens.MovieDetailsPackage.SerialDetailsScreen
import com.example.jetfilms.Screens.ParticipantDetailsPackage.ParticipantDetailsScreen
import com.example.jetfilms.Screens.SearchScreen.SearchScreen
import com.example.jetfilms.Screens.Start.StartScreen
import com.example.jetfilms.Screens.StartScreen
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MainScreen(

) {
    val screensNavController = rememberNavController()
    var showBottomBar by remember{ mutableStateOf(false) }

    val hazeState = remember { HazeState() }

    var homeDelay by remember{ mutableStateOf(0) }

    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available

    val scope = rememberCoroutineScope()

    if(isConnected){
        val moviesViewModel: MoviesViewModel = hiltViewModel()

        val cast = moviesViewModel.selectedMovieCast.collectAsStateWithLifecycle()
        val movieImages = moviesViewModel.selectedMovieImages.collectAsStateWithLifecycle()
        val similarMovies = moviesViewModel.similarMovies.collectAsStateWithLifecycle()

        val participantFilmography = moviesViewModel.selectedParticipantFilmography.collectAsStateWithLifecycle()
        val participantImages = moviesViewModel.selectedParticipantImages.collectAsStateWithLifecycle()

        Scaffold(
            containerColor = Color.Black,
            bottomBar = {
                BottomNavBar(
                    navController = screensNavController,
                    hazeState = hazeState,
                    showBottomBar = showBottomBar
                )
            },
        ) {
            NavHost(
                navController = screensNavController,
                startDestination = "HomeScreen",
                modifier = Modifier
                    //    .padding(paddingValues)
                    .fillMaxSize()
                    .haze(
                        hazeState,
                        backgroundColor = hazeStateBlurBackground,
                        tint = hazeStateBlurTint,
                        blurRadius = HAZE_STATE_BLUR.sdp,
                    )
            ) {
                composable<StartScreen> {
                    showBottomBar = false
                    StartScreen()
                }

                composable(
                    route = "HomeScreen",
                    enterTransition = { fadeIn(tween(delayMillis = 350)) }
                ) {
                    homeDelay = 70
                    showBottomBar = true

                    HomeScreen(screensNavController, moviesViewModel)
                }

                composable(
                    route = "ExploreScreen"
                ) {
                    showBottomBar = true

                    SearchScreen(
                        navController = screensNavController,
                        moviesViewModel = moviesViewModel
                    )
                }

                composable(
                    route = "FavoriteScreen"
                ) {

                }

                composable(
                    route = "AccountScreen"
                ) {

                }

                composable<MoreMoviesScreenRoute> {
                    homeDelay = 70
                    showBottomBar = true

                    val category =
                        screensNavController.currentBackStackEntry?.arguments?.getString("category")

                    category?.let {
                        MoreMoviesScreen(
                            navController = screensNavController,
                            category = category.toString(),
                            moviesViewModel = moviesViewModel,
                        )
                    }
                }

                composable<MoreSerialsScreenRoute> {
                    homeDelay = 70
                    showBottomBar = true

                    val category =
                        screensNavController.currentBackStackEntry?.arguments?.getString("category")

                    category?.let {
                        MoreSerialsScreen(
                            navController = screensNavController,
                            category = category.toString(),
                            moviesViewModel = moviesViewModel,
                        )
                    }
                }

                composable(
                    route = "movie_details/{movie}",
                    arguments = listOf(navArgument("movie") { type = DetailedMovieNavType() }),
                ) {
                    homeDelay = 350
                    showBottomBar = false

                    val movieResponse = it.arguments?.getParcelable<DetailedMovieResponse>("movie")
                    movieResponse?.let {
                        LaunchedEffect(null) {
                            moviesViewModel.setSelectedMovieAdditions(movieResponse.id)
                        }

                        if(cast.value != null && similarMovies.value != null) {

                            MovieDetailsScreen(
                                navController = screensNavController,
                                movieDisplay = MovieDisplay(
                                    response = movieResponse,
                                    movieCast = cast.value!!,
                                    movieImages = movieImages.value,
                                    similarMovies = similarMovies.value!!
                                ),
                                selectMovie = {movie ->
                                    scope.launch {
                                        moviesViewModel.getMovie(movie.id)?.let { detailedMovie ->
                                            navigateToSelectedMovie(screensNavController, detailedMovie)
                                        }
                                    }
                                },
                                selectParticipant = { participant ->
                                    scope.launch {
                                        navigateToSelectedParticipant(navController = screensNavController,moviesViewModel.getParticipant(participant.id))
                                    }
                                }
                            )
                        }
                    }

                }

                composable(
                    route = "serial_details/{serial}",
                    arguments = listOf(navArgument("serial") { type = DetailedSerialNavType() }),
                ) {
                    homeDelay = 350
                    showBottomBar = false

                    val serialResponse = it.arguments?.getParcelable<DetailedSerialResponse>("serial")
                    serialResponse?.let {

                        SerialDetailsScreen(
                            navController = screensNavController,
                            serialResponse = serialResponse,
                            selectSeason = { serialId, seasonNumber ->
                                try {
                                    moviesViewModel.getSerialSeason(serialId, seasonNumber)
                                }
                                catch (e:Exception){
                                    Log.e("error",e.message.toString())
                                    Log.d("n",(seasonNumber+1).toString())
                                    moviesViewModel.getSerialSeason(serialId, seasonNumber + 1)
                                }

                            }
                        )
                    }

                }

                composable(
                    route = "participant_details/{participant}",
                    arguments = listOf(navArgument("participant") { type = ParticipantNavType() }),
                ) {
                    homeDelay = 350
                    showBottomBar = false

                    val participantResponse = it.arguments?.getParcelable<DetailedParticipantResponse>("participant")
                    participantResponse?.let {
                        LaunchedEffect(null) {
                            moviesViewModel.setParticipantFilmography(participantResponse.id)
                            moviesViewModel.setParticipantImages(participantResponse.id)
                        }

                        if(participantFilmography.value != null && participantImages.value != null) {
                            ParticipantDetailsScreen(
                                navController = screensNavController,
                                participantDisplay = DetailedParticipantDisplay(
                                    participantResponse = participantResponse,
                                    filmography = participantFilmography.value!!,
                                    images = participantImages.value!!,
                                ),
                                selectMovie = {movie ->
                                    scope.launch {
                                        moviesViewModel.getMovie(movie.id)?.let { detailedMovie ->
                                            navigateToSelectedMovie(screensNavController, detailedMovie)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
private fun mainscreen() {
    MainScreen()
}