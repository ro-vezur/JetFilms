package com.example.jetfilms.View.Screens

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
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Helpers.navigate.navigateToSelectedMovie
import com.example.jetfilms.Helpers.navigate.navigateToSelectedParticipant
import com.example.jetfilms.View.Components.Bottom_Navigation_Bar.BottomNavBar
import com.example.jetfilms.extensions.CustomNavType.DetailedMovieNavType
import com.example.jetfilms.extensions.CustomNavType.DetailedSerialNavType
import com.example.jetfilms.extensions.CustomNavType.ParticipantNavType
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.MovieDisplay
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SerialDisplay
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.SimplifiedParticipantResponse
import com.example.jetfilms.Helpers.DTOsConverters.MovieDataToUnifiedMedia
import com.example.jetfilms.Helpers.DTOsConverters.SeriesDataToUnifiedMedia
import com.example.jetfilms.Helpers.navigate.navigateToSelectedSerial
import com.example.jetfilms.Helpers.Network.ConnectionState
import com.example.jetfilms.Helpers.Network.connectivityState
import com.example.jetfilms.View.Screens.Favorite.FavoriteNavigateScreen
import com.example.jetfilms.View.Screens.Home.HomeScreen
import com.example.jetfilms.View.Screens.Home.MoreMoviesScreen
import com.example.jetfilms.View.Screens.Home.MoreSerialsScreen
import com.example.jetfilms.View.Screens.DetailedMediaScreens.MovieDetailsPackage.MovieDetailsScreen
import com.example.jetfilms.Screens.MovieDetailsPackage.SerialDetailsScreen
import com.example.jetfilms.View.Screens.Account.NavigateAccountScreen
import com.example.jetfilms.View.Screens.ParticipantDetailsPackage.ParticipantDetailsScreen
import com.example.jetfilms.View.Screens.SearchScreen.FilterUI.FiltersMainScreen
import com.example.jetfilms.View.Screens.SearchScreen.SearchScreen
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.View.Screens.Start.StartScreen
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.ViewModels.ParticipantViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ViewModels.SeriesViewModel
import com.example.jetfilms.ViewModels.UnifiedMediaViewModel
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore
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

    val turnBack = { screensNavController.popBackStackOrIgnore() }

    if(isConnected){
        val userViewModel: UserViewModel = hiltViewModel()
        val firebaseUser = userViewModel.firebaseUser.collectAsStateWithLifecycle()

        if(firebaseUser.value != null) {

            val moviesViewModel: MoviesViewModel = hiltViewModel()
            val seriesViewModel: SeriesViewModel = hiltViewModel()
            val participantViewModel: ParticipantViewModel = hiltViewModel()
            val unifiedMediaViewModel: UnifiedMediaViewModel = hiltViewModel()
            val searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel()


            val selectedMediaCast = unifiedMediaViewModel.selectedMediaCast.collectAsStateWithLifecycle()
            val selectedMediaImages = unifiedMediaViewModel.selectedMediaImages.collectAsStateWithLifecycle()
            val selectedMediaTrailers = unifiedMediaViewModel.selectedMediaTrailers.collectAsStateWithLifecycle()

            val similarMovies = moviesViewModel.similarMovies.collectAsStateWithLifecycle()
            val similarSeries = seriesViewModel.similarSerials.collectAsStateWithLifecycle()

            val participantFilmography = participantViewModel.selectedParticipantFilmography.collectAsStateWithLifecycle()
            val participantImages = participantViewModel.selectedParticipantImages.collectAsStateWithLifecycle()

            val user = userViewModel.user.collectAsStateWithLifecycle()

            val selectMovie: (movieId: Int) -> Unit = { id ->
                try {
                    scope.launch {
                        moviesViewModel.getMovie(id)?.let { detailedMovie ->
                            navigateToSelectedMovie(screensNavController, detailedMovie)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("error", e.message.toString())
                }
            }

            val selectSeries: (seriesId: Int) -> Unit = { id ->
                try {
                    scope.launch {
                        navigateToSelectedSerial(
                            screensNavController,
                            seriesViewModel.getSerial(id)
                        )
                    }
                } catch (_: Exception) {
                }
            }

            val selectParticipant: (participant: SimplifiedParticipantResponse) -> Unit =
                { participant ->
                    scope.launch {
                        navigateToSelectedParticipant(
                            navController = screensNavController,
                            participantViewModel.getParticipant(participant.id)
                        )
                    }
                }

            LaunchedEffect(null) {
                val searchedHistoryMediaIds = searchHistoryViewModel.getSearchHistoryMediaIds()

             //   Log.d("user",user.value.toString())

                searchedHistoryMediaIds.forEach { searchedMedia: SearchedMedia ->
                    scope.launch {
                        if (searchedMedia.mediaType == MediaFormats.MOVIE.format) {
                            val movie = moviesViewModel.getMovie(searchedMedia.mediaId)
                            movie?.let {
                                searchHistoryViewModel.addSearchHistoryMedia(
                                    MovieDataToUnifiedMedia(movie), searchedMedia
                                )
                            }
                        } else {
                            val series = seriesViewModel.getSerial(searchedMedia.mediaId)
                            searchHistoryViewModel.addSearchHistoryMedia(
                                SeriesDataToUnifiedMedia(series), searchedMedia
                            )
                        }
                    }
                }



                user.value?.let { checkedUser ->
                    unifiedMediaViewModel.setFavoriteMedia(checkedUser.favoriteMediaList)
                  //  Log.d("favorite media list in launched effect",checkedUser.favoriteMediaList.toString())
                }


            }

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
                        .fillMaxSize()
                        .haze(
                            hazeState,
                            backgroundColor = hazeStateBlurBackground,
                            tint = hazeStateBlurTint,
                            blurRadius = HAZE_STATE_BLUR.sdp,
                        )
                ) {

                    composable(
                        route = "HomeScreen",
                        enterTransition = { fadeIn(tween(delayMillis = 350)) }
                    ) {
                        homeDelay = 70
                        showBottomBar = true

                        HomeScreen(
                            selectMovie = selectMovie,
                            selectSeries = selectSeries,
                            navController = screensNavController,
                            moviesViewModel = moviesViewModel,
                            seriesViewModel = seriesViewModel
                        )
                    }

                    composable(
                        route = "ExploreScreen"
                    ) {
                        showBottomBar = true

                        SearchScreen(
                            selectMovie = selectMovie,
                            selectSeries = selectSeries,
                            navController = screensNavController,
                            moviesViewModel = moviesViewModel,
                            seriesViewModel = seriesViewModel,
                            unifiedMediaViewModel = unifiedMediaViewModel,
                            searchHistoryViewModel = searchHistoryViewModel,
                        )
                    }

                    composable(
                        route = "FavoriteScreen"
                    ) {
                        showBottomBar = true

                        FavoriteNavigateScreen(
                            searchHistoryViewModel = searchHistoryViewModel,
                            unifiedMediaViewModel = unifiedMediaViewModel,
                            selectMedia = { unifiedMedia ->
                                if (unifiedMedia.mediaType == MediaFormats.MOVIE) {
                                    selectMovie(unifiedMedia.id)
                                } else {
                                    selectSeries(unifiedMedia.id)
                                }
                            }
                        )
                    }

                    composable(
                        route = "AccountScreen"
                    ) {
                       NavigateAccountScreen(
                           userViewModel = userViewModel
                       )
                    }

                    composable<MoreMoviesScreenRoute> {
                        homeDelay = 70
                        showBottomBar = true

                        val category =
                            screensNavController.currentBackStackEntry?.arguments?.getString("category")

                        category?.let {
                            MoreMoviesScreen(
                                turnBack = turnBack,
                                selectMovie = selectMovie,
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
                                selectSeries = selectSeries,
                                navController = screensNavController,
                                category = category.toString(),
                                seriesViewModel = seriesViewModel,
                            )
                        }
                    }

                    composable(
                        route = "movie_details/{movie}",
                        arguments = listOf(navArgument("movie") { type = DetailedMovieNavType() }),
                    ) { route ->
                        homeDelay = 350
                        showBottomBar = false

                        val movieResponse =
                            route.arguments?.getParcelable<DetailedMovieResponse>("movie")
                        movieResponse?.let {
                            LaunchedEffect(null) {
                                unifiedMediaViewModel.setMoviesExtraInformation(movieResponse.id)
                                moviesViewModel.setSimilarMovies(movieResponse.id)
                            }

                            if (similarMovies.value != null) {
                                MovieDetailsScreen(
                                    navController = screensNavController,
                                    movieDisplay = MovieDisplay(
                                        response = movieResponse,
                                        movieCast = selectedMediaCast.value,
                                        movieImages = selectedMediaImages.value,
                                        similarMovies = similarMovies.value!!,
                                        movieTrailers = selectedMediaTrailers.value
                                    ),
                                    selectMovie = selectMovie,
                                    selectParticipant = selectParticipant,
                                    addToFavorite = { favoriteMedia ->
                                        userViewModel.addFavoriteMedia(favoriteMedia)
                                        unifiedMediaViewModel.addFavoriteMedia(favoriteMedia)
                                                    },
                                    isFavoriteUnit = { favoriteMedia ->
                                        user.value?.favoriteMediaList?.find { it.id == favoriteMedia.id } != null
                                    }
                                )
                            }
                        }
                    }

                    composable(
                        route = "serial_details/{serial}",
                        arguments = listOf(navArgument("serial") {
                            type = DetailedSerialNavType()
                        }),
                        enterTransition = { fadeIn(animationSpec = tween(75)) }
                    ) {
                        homeDelay = 350
                        showBottomBar = false

                        val serialResponse =
                            it.arguments?.getParcelable<DetailedSerialResponse>("serial")
                        serialResponse?.let {
                            LaunchedEffect(null) {
                                unifiedMediaViewModel.setSeriesExtraInformation(serialResponse.id)
                                seriesViewModel.setSimilarSerials(serialResponse.id)
                            }

                            if (similarSeries.value != null) {
                                SerialDetailsScreen(
                                    navController = screensNavController,
                                    serialDisplay = SerialDisplay(
                                        response = serialResponse,
                                        serialCast = selectedMediaCast.value,
                                        serialImages = selectedMediaImages.value,
                                        similarSerials = similarSeries.value!!,
                                        seriesTrailers = selectedMediaTrailers.value
                                    ),
                                    selectSeason = { serialId, seasonNumber ->
                                        try {
                                            seriesViewModel.getSerialSeason(serialId, seasonNumber)
                                        } catch (e: Exception) {
                                            seriesViewModel.getSerialSeason(
                                                serialId,
                                                seasonNumber + 1
                                            )
                                        }
                                    },
                                    selectSerial = selectSeries,
                                    selectParticipant = selectParticipant,
                                    addToFavorite = {favoriteMedia -> userViewModel.addFavoriteMedia(favoriteMedia) }
                                )
                            }
                        }
                    }

                    composable(
                        route = "participant_details/{participant}",
                        arguments = listOf(navArgument("participant") {
                            type = ParticipantNavType()
                        }),
                    ) {
                        homeDelay = 350
                        showBottomBar = false

                        val participantResponse =
                            it.arguments?.getParcelable<DetailedParticipantResponse>("participant")
                        participantResponse?.let {
                            LaunchedEffect(null) {
                                participantViewModel.setParticipantFilmography(participantResponse.id)
                                participantViewModel.setParticipantImages(participantResponse.id)
                            }

                            if (participantFilmography.value != null && participantImages.value != null) {
                                ParticipantDetailsScreen(
                                    navController = screensNavController,
                                    participantDisplay = DetailedParticipantDisplay(
                                        participantResponse = participantResponse,
                                        filmography = participantFilmography.value!!,
                                        images = participantImages.value!!,
                                    ),
                                    selectMovie = selectMovie
                                )
                            }
                        }
                    }

                    composable("FilterScreen") {
                        showBottomBar = true
                        FiltersMainScreen(
                            turnOffFilter = turnBack,
                            moviesViewModel = moviesViewModel,
                            seriesViewModel = seriesViewModel,
                            unifiedMediaViewModel = unifiedMediaViewModel,
                        )
                    }
                }
            }
        } else {
            Scaffold(
                containerColor = Color.Black,
            ) {
                StartScreen(
                    userViewModel = userViewModel
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