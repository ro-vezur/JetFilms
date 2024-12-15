package com.example.jetfilms.View.Screens

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.SimplifiedParticipantResponse
import com.example.jetfilms.Helpers.navigate.navigateToSelectedSerial
import com.example.jetfilms.Helpers.Network.ConnectionState
import com.example.jetfilms.Helpers.Network.connectivityState
import com.example.jetfilms.View.Screens.Favorite.FavoriteNavigateScreen
import com.example.jetfilms.View.Screens.Home.HomeScreen
import com.example.jetfilms.View.Screens.Home.MoreMoviesScreen
import com.example.jetfilms.View.Screens.Home.MoreSerialsScreen
import com.example.jetfilms.View.Screens.DetailedMediaScreens.MovieDetailsPackage.MovieDetailsScreen
import com.example.jetfilms.Screens.MovieDetailsPackage.SerialDetailsScreen
import com.example.jetfilms.View.Screens.Account.accountNavigationHost
import com.example.jetfilms.View.Screens.ParticipantDetailsPackage.ParticipantDetailsScreen
import com.example.jetfilms.View.Screens.SearchScreen.exploreScreen
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.View.Screens.Start.StartScreen
import com.example.jetfilms.ViewModels.FilterViewModel
import com.example.jetfilms.ViewModels.SharedMoviesViewModel
import com.example.jetfilms.ViewModels.ParticipantViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ViewModels.SharedSeriesViewModel
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

            val sharedMoviesViewModel: SharedMoviesViewModel = hiltViewModel()
            val sharedSeriesViewModel: SharedSeriesViewModel = hiltViewModel()
            val participantViewModel: ParticipantViewModel = hiltViewModel()

            val searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel()
            val filterViewModel: FilterViewModel = hiltViewModel()

            val user = userViewModel.user.collectAsStateWithLifecycle()

            val currentBackStackEntry by screensNavController.currentBackStackEntryAsState()

            val selectMovie: (movieId: Int) -> Unit = { id ->
                try {
                    scope.launch {
                        navigateToSelectedMovie(
                            screensNavController,
                            sharedMoviesViewModel.getMovie(id)
                        )
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
                            sharedSeriesViewModel.getSerial(id)
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
                val searchedHistoryMediaIds = searchHistoryViewModel.getAllSearchHistory()

                searchedHistoryMediaIds.forEach { searchedMedia: SearchedMedia ->
                    scope.launch {
                        if (searchedMedia.mediaType == MediaFormats.MOVIE.format) {
                            searchHistoryViewModel.addMovieToFlow(searchedMedia.mediaId)
                        } else {
                            searchHistoryViewModel.addSeriesToFlow(searchedMedia.mediaId )
                        }
                    }
                }
            }

            LaunchedEffect(user.value?.favoriteMediaList?.size) {
                user.value?.let { checkedUser ->
                  //  unifiedMediaViewModel.setFavoriteMedia(checkedUser.favoriteMediaList)
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
                    startDestination = HomeScreen,
                    modifier = Modifier
                        .fillMaxSize()
                        .haze(
                            hazeState,
                            backgroundColor = hazeStateBlurBackground,
                            tint = hazeStateBlurTint,
                            blurRadius = HAZE_STATE_BLUR.sdp,
                        )
                ) {

                    composable<HomeScreen>(
                        enterTransition = { fadeIn(tween(delayMillis = 350)) }
                    ) {
                        homeDelay = 70
                        showBottomBar = true

                        HomeScreen(
                            selectMovie = selectMovie,
                            selectSeries = selectSeries,
                            navController = screensNavController,
                            moviesViewModel = sharedMoviesViewModel,
                            seriesViewModel = sharedSeriesViewModel,
                            addToFavorite = { favoriteMedia ->
                                userViewModel.addFavoriteMedia(favoriteMedia)
                            },
                            isFavoriteUnit = { favoriteMedia ->
                                user.value?.favoriteMediaList?.find { it.id == favoriteMedia.id } != null
                            }
                        )
                    }

                    exploreScreen(
                        screensNavController = screensNavController,
                        showBottomBar = { show -> showBottomBar = show},
                        seeAllMedia = { type, query ->
                            if (type == MediaFormats.MOVIE) {
                                screensNavController.navigate(MoreMoviesScreenRoute("Searched Movies"))
                                sharedMoviesViewModel.setMoreMoviesView(
                                    response = { page -> sharedMoviesViewModel.searchMovies(query, page) }
                                )
                            } else {
                                screensNavController.navigate(MoreSerialsScreenRoute("Searched Series"))
                                sharedSeriesViewModel.setMoreSerialsView(
                                    response = { page -> sharedSeriesViewModel.searchSeries(query, page) }
                                )
                            }
                        },
                        selectMedia = { id, type ->
                            if (type == MediaFormats.MOVIE) {
                                selectMovie(id)
                            } else {
                                selectSeries(id)
                            }
                        },
                        filterViewModel = filterViewModel,
                        searchHistoryViewModel = searchHistoryViewModel,
                    )

                    composable<FavoriteScreen>() {
                        showBottomBar = true

                        val searchedUnifiedMedia by searchHistoryViewModel.searchedUnifiedMedia.collectAsStateWithLifecycle()
                        val searchedMediaInDb by searchHistoryViewModel.searchedHistoryMedia.collectAsStateWithLifecycle()

                        val searchedHistory = searchedUnifiedMedia.sortedByDescending { unifiedMedia ->
                            searchedMediaInDb.find { mediaFromDb -> unifiedMedia.id == mediaFromDb.mediaId }?.viewedDateMillis
                        }.take(10)

                        user.value?.let { checkedUser ->
                            FavoriteNavigateScreen(
                                searchedHistoryFlow = searchedHistory,
                                searchedHistoryInDb = searchedMediaInDb,
                                user = checkedUser,
                                selectMedia = { unifiedMedia ->
                                    if (unifiedMedia.mediaType == MediaFormats.MOVIE) {
                                        selectMovie(unifiedMedia.id)
                                    } else {
                                        selectSeries(unifiedMedia.id)
                                    }
                                }
                            )
                        }
                    }

                    accountNavigationHost(
                        navController = screensNavController,
                        userViewModel = userViewModel,
                    )

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
                                moviesViewModel = sharedMoviesViewModel,
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
                                seriesViewModel = sharedSeriesViewModel,
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
                            MovieDetailsScreen(
                                navController = screensNavController,
                                movieResponse = movieResponse,
                                selectMovie = selectMovie,
                                selectParticipant = selectParticipant,
                                addToFavorite = { favoriteMedia ->
                                    userViewModel.addFavoriteMedia(favoriteMedia)
                                },
                                isFavoriteUnit = { favoriteMedia ->
                                    user.value?.favoriteMediaList?.find { it.id == favoriteMedia.id } != null
                                }
                            )
                        }
                    }

                    composable(
                        route = "serial_details/{serial}",
                        arguments = listOf(navArgument("serial") {
                            type = DetailedSerialNavType()
                        }),
                        enterTransition = { fadeIn(animationSpec = tween(75)) }
                    ) { route ->
                        homeDelay = 350
                        showBottomBar = false

                        val seriesResponse = route.arguments?.getParcelable<DetailedSerialResponse>("serial")
                        seriesResponse?.let {
                            SerialDetailsScreen(
                                navController = screensNavController,
                                seriesResponse = seriesResponse,
                                selectSeason = { serialId, seasonNumber ->
                                    try {
                                        sharedSeriesViewModel.getSerialSeason(serialId, seasonNumber)
                                    } catch (e: Exception) {
                                        sharedSeriesViewModel.getSerialSeason(
                                            serialId,
                                            seasonNumber + 1
                                        )
                                    }
                                },
                                selectSerial = selectSeries,
                                selectParticipant = selectParticipant,
                                addToFavorite = { favoriteMedia ->
                                    userViewModel.addFavoriteMedia(favoriteMedia)
                                },
                                isFavoriteUnit = { favoriteMedia ->
                                    user.value?.favoriteMediaList?.find { it.id == favoriteMedia.id } != null
                                }
                            )
                        }
                    }

                    composable(
                        route = "participant_details/{participant}",
                        arguments = listOf(navArgument("participant") {
                            type = ParticipantNavType()
                        }),
                    ) {
                        val participantFilmography = participantViewModel.selectedParticipantFilmography.collectAsStateWithLifecycle()
                        val participantImages = participantViewModel.selectedParticipantImages.collectAsStateWithLifecycle()

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

@Preview
@Composable
private fun mainscreen() {
    MainScreen()
}