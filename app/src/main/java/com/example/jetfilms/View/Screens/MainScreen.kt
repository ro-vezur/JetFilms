package com.example.jetfilms.View.Screens

import android.annotation.SuppressLint
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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Helpers.navigate.navigateToSelectedMovie
import com.example.jetfilms.View.Components.Bottom_Navigation_Bar.BottomNavBar
import com.example.jetfilms.extensions.CustomNavType.DetailedMovieNavType
import com.example.jetfilms.extensions.CustomNavType.DetailedSerialNavType
import com.example.jetfilms.extensions.CustomNavType.ParticipantNavType
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantResponse
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSerialResponse
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
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.View.Screens.Start.StartScreen
import com.example.jetfilms.ViewModels.SharedMoviesViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ViewModels.SharedSeriesViewModel
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
@Composable
fun MainScreen(

) {
    val screensNavController = rememberNavController()
    var showBottomBar by remember{ mutableStateOf(false) }
    val hazeState = remember { HazeState() }

    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available

    val scope = rememberCoroutineScope()

    val turnBack = { screensNavController.popBackStackOrIgnore() }

    if(isConnected){
        val userViewModel: UserViewModel = hiltViewModel()
        val firebaseUser = userViewModel.firebaseUser.collectAsStateWithLifecycle()

        if(firebaseUser.value != null) {

            val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
                "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
            }

            val sharedMoviesViewModel: SharedMoviesViewModel = hiltViewModel()
            val sharedSeriesViewModel: SharedSeriesViewModel = hiltViewModel()

            val searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel()

            val user = userViewModel.user.collectAsStateWithLifecycle()

            val selectMovie: (movieId: Int) -> Unit = { id ->
                try {
                    scope.launch {
                        navigateToSelectedMovie(
                            screensNavController,
                            sharedMoviesViewModel.getMovie(id)
                        )
                    }
                } catch (_: Exception) {
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

            LaunchedEffect(null) {
                val searchedHistoryMediaIds = searchHistoryViewModel.getAllSearchHistory()

                searchedHistoryMediaIds.forEach { searchedMedia: SearchedMedia ->
                    scope.launch {
                        if (searchedMedia.mediaType == MediaCategories.MOVIE.format) {
                            searchHistoryViewModel.addMovieToFlow(searchedMedia.mediaId)
                        } else {
                            searchHistoryViewModel.addSeriesToFlow(searchedMedia.mediaId )
                        }
                    }
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
                    startDestination = HomeRoute,
                    modifier = Modifier
                        .fillMaxSize()
                        .haze(
                            hazeState,
                            backgroundColor = hazeStateBlurBackground,
                            tint = hazeStateBlurTint,
                            blurRadius = HAZE_STATE_BLUR.sdp,
                        )
                ) {

                    composable<HomeRoute>(
                        enterTransition = { fadeIn(tween(delayMillis = 350)) }
                    ) {
                        showBottomBar = true

                        HomeScreen(
                            selectMovie = selectMovie,
                            selectSeries = selectSeries,
                            navController = screensNavController,
                            addToFavorite = { favoriteMedia ->
                                userViewModel.addFavoriteMedia(favoriteMedia)
                            },
                            isFavoriteUnit = { favoriteMedia ->
                                user.value?.favoriteMediaList?.find { it.id == favoriteMedia.id } != null
                            },
                            homeViewModel = hiltViewModel(viewModelStoreOwner)
                        )
                    }

                    exploreScreen(
                        navController = screensNavController,
                        showBottomBar = { show -> showBottomBar = show},
                        seeAllMedia = { type, query ->
                            if (type == MediaCategories.MOVIE) {
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
                            if (type == MediaCategories.MOVIE) {
                                selectMovie(id)
                            } else {
                                selectSeries(id)
                            }
                        },
                        searchHistoryViewModel = searchHistoryViewModel,
                    )

                    composable<FavoriteRoute>() {
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
                                    if (unifiedMedia.mediaType == MediaCategories.MOVIE) {
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
                        showBottomBar = { show -> showBottomBar = show}
                    )

                    composable<MoreMoviesScreenRoute> {
                        showBottomBar = true

                        val category = screensNavController.currentBackStackEntry?.arguments?.getString("category")

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
                        showBottomBar = true

                        val category = screensNavController.currentBackStackEntry?.arguments?.getString("category")

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
                        showBottomBar = false

                        val movieResponse =
                            route.arguments?.getParcelable<DetailedMovieResponse>("movie")
                        movieResponse?.let {
                            MovieDetailsScreen(
                                navController = screensNavController,
                                movieResponse = movieResponse,
                                selectMovie = selectMovie,
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
                        showBottomBar = false

                        val seriesResponse = route.arguments?.getParcelable<DetailedSerialResponse>("serial")
                        seriesResponse?.let {
                            SerialDetailsScreen(
                                navController = screensNavController,
                                seriesResponse = seriesResponse,
                                selectSerial = selectSeries,
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
                        showBottomBar = false

                        val participantResponse = it.arguments?.getParcelable<DetailedParticipantResponse>("participant")

                        participantResponse?.let {
                            ParticipantDetailsScreen(
                                navController = screensNavController,
                                participantResponse = participantResponse,
                                selectMovie = selectMovie
                            )
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