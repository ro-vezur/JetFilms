package com.example.jetfilms

import android.annotation.SuppressLint
import android.os.Build
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
import com.example.jetfilms.Additional_functions.navigate.navigateToSelectedMovie
import com.example.jetfilms.Additional_functions.navigate.navigateToSelectedParticipant
import com.example.jetfilms.Bottom_Navigation_Bar.BottomNavBar
import com.example.jetfilms.Custom_NavType.DetailedMovieNavType
import com.example.jetfilms.Custom_NavType.ParticipantNavType
import com.example.jetfilms.Data_Classes.MoviePackage.DetailedMovieDataClassResponse
import com.example.jetfilms.Data_Classes.MoviePackage.DetailedMovieDisplay
import com.example.jetfilms.Data_Classes.ParticipantPackage.DetailedMovieParticipantResponse
import com.example.jetfilms.Data_Classes.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.Network.ConnectionState
import com.example.jetfilms.Network.connectivityState
import com.example.jetfilms.Screens.Home.HomeScreen
import com.example.jetfilms.Screens.Home.MoreMoviesScreen
import com.example.jetfilms.Screens.MoreMoviesScreenRoute
import com.example.jetfilms.Screens.MovieDetailsPackage.MovieDetailsScreen
import com.example.jetfilms.Screens.ParticipantDetailsPackage.ParticipantDetailsScreen
import com.example.jetfilms.Screens.Start.StartScreen
import com.example.jetfilms.Screens.StartScreen
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.extensions.sdp
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
                        backgroundColor = Color.DarkGray,
                        tint = Color.Black.copy(alpha = .25f),
                        blurRadius = 28.sdp,
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

                }

                composable(
                    route = "TVScreen"
                ) {

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

                composable(
                    route = "movie_details/{movie}",
                    arguments = listOf(navArgument("movie") { type = DetailedMovieNavType() }),
                ) {
                    homeDelay = 350
                    showBottomBar = false

                    val movieResponse = it.arguments?.getParcelable<DetailedMovieDataClassResponse>("movie")
                    if(movieResponse != null && cast.value != null && similarMovies.value != null) {
                        LaunchedEffect(null) {
                            moviesViewModel.setSelectedMovieAdditions(movieResponse.id)
                        }

                        MovieDetailsScreen(
                            navController = screensNavController,
                            movieDisplay = DetailedMovieDisplay(
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

                composable(
                    route = "participant_details/{participant}",
                    arguments = listOf(navArgument("participant") { type = ParticipantNavType() }),
                ) {
                    homeDelay = 350
                    showBottomBar = false

                    val participantResponse = it.arguments?.getParcelable<DetailedMovieParticipantResponse>("participant")
                    if(participantResponse != null && cast.value != null && similarMovies.value != null) {
                        LaunchedEffect(null) {
                            //moviesViewModel.setSelectedMovieAdditions(participantResponse.id)
                        }

                        ParticipantDetailsScreen(
                            navController = screensNavController,
                            participantDisplay = DetailedParticipantDisplay(
                                participantResponse = participantResponse
                            ),
                            selectMovie = {movie ->
                                scope.launch {
                                    moviesViewModel.getMovie(movie.id)?.let { detailedMovie ->
                                  //      NavigateToSelectedMovie(screensNavController, detailedMovie)
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

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
private fun mainscreen() {
    MainScreen()
}