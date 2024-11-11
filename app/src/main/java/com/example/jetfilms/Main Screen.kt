package com.example.jetfilms

import android.annotation.SuppressLint
import android.os.Build
import android.transition.Fade
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.jetfilms.Bottom_Navigation_Bar.BottomNavBar
import com.example.jetfilms.Custom_NavType.DetailedMovieNavType
import com.example.jetfilms.Data_Classes.DetailedMovieDataClass
import com.example.jetfilms.Screens.Home.HomeScreen
import com.example.jetfilms.Screens.Home.MoreMoviesScreen
import com.example.jetfilms.Screens.MoreMoviesScreenRoute
import com.example.jetfilms.Screens.MovieDetailsScreen
import com.example.jetfilms.Screens.Start.StartScreen
import com.example.jetfilms.Screens.StartScreen
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.extensions.sdp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MainScreen(
    moviesViewModel: MoviesViewModel = hiltViewModel(),
) {
    val screensNavController = rememberNavController()
    var showBottomBar by remember{ mutableStateOf(false) }

    val hazeState = remember { HazeState() }

    var homeDelay by remember{ mutableStateOf(0) }

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
        ){
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

                HomeScreen(screensNavController,moviesViewModel)
            }

            composable(
                route = "ExploreScreen"
            ){

            }

            composable(
                route = "TVScreen"
            ){

            }

            composable(
                route = "FavoriteScreen"
            ){

            }

            composable(
                route = "AccountScreen"
            ){

            }

            composable<MoreMoviesScreenRoute> {
                homeDelay = 70
                showBottomBar = true

                val category = screensNavController.currentBackStackEntry?.arguments?.getString("category")

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

                val movie =  it.arguments?.getParcelable<DetailedMovieDataClass>("movie")

                MovieDetailsScreen(
                    navController = screensNavController,
                    movie = movie
                )



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