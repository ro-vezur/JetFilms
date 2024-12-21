package com.example.jetfilms.View.Screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Helpers.navigate.navigateToSelectedMovie
import com.example.jetfilms.View.Components.Bottom_Navigation_Bar.BottomNavBar
import com.example.jetfilms.extensions.CustomNavType.DetailedMovieNavType
import com.example.jetfilms.extensions.CustomNavType.DetailedSerialNavType
import com.example.jetfilms.extensions.CustomNavType.ParticipantNavType
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantResponse
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
import com.example.jetfilms.View.Screens.Home.MoreUnifiedScreen
import com.example.jetfilms.View.Screens.ParticipantDetailsPackage.ParticipantDetailsScreen
import com.example.jetfilms.View.Screens.SearchScreen.exploreScreen
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.View.Screens.Start.StartScreen
import com.example.jetfilms.ViewModels.HomeViewModel
import com.example.jetfilms.ViewModels.SharedMediaViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ViewModels.UserViewModel.UserViewModel
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
        val firebaseUser by userViewModel.firebaseUser.collectAsStateWithLifecycle()


        val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        }

        val sharedMediaViewModel: SharedMediaViewModel = hiltViewModel()


        val initialRoute = if(firebaseUser != null) {
            HomeRoute
        } else
            StartScreen

        val selectMovie: (movieId: Int) -> Unit = { id ->
            try {
                scope.launch {
                    navigateToSelectedMovie(
                        screensNavController,
                        sharedMediaViewModel.getMovie(id)
                    )
                }
            } catch (_: Exception) { }
        }

        val selectSeries: (seriesId: Int) -> Unit = { id ->
            try {
                scope.launch {
                    navigateToSelectedSerial(
                        screensNavController,
                        sharedMediaViewModel.getSerial(id)
                    )
                }
            } catch (_: Exception) { }
        }

        Scaffold(
            containerColor = Color.Black,
            bottomBar = {
                if(firebaseUser != null){
                    BottomNavBar(
                        navController = screensNavController,
                        hazeState = hazeState,
                        showBottomBar = showBottomBar
                    )
                }
            },
        ) {
            NavHost(
                navController = screensNavController,
                startDestination = initialRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .haze(
                        hazeState,
                        backgroundColor = hazeStateBlurBackground,
                        tint = hazeStateBlurTint,
                        blurRadius = HAZE_STATE_BLUR.sdp,
                    )
            ) {

                composable<StartScreen> {
                    Scaffold(
                        containerColor = Color.Black,
                    ) {
                        StartScreen(
                            userViewModel = userViewModel
                        )
                    }
                }

                composable<HomeRoute>(
                    enterTransition = { fadeIn(tween(delayMillis = 350)) }
                ) {
                    showBottomBar = true

                    val user by userViewModel.user.collectAsStateWithLifecycle()

                    user?.let { checkedUser ->
                        val homeViewModel = hiltViewModel<HomeViewModel,HomeViewModel.HomeViewModelFactory>(viewModelStoreOwner) { factory ->
                            factory.create(checkedUser)
                        }

                        HomeScreen(
                            selectMovie = selectMovie,
                            selectSeries = selectSeries,
                            seeAllMedia = { type ->
                                if(type == MediaCategories.MOVIE) {
                                    sharedMediaViewModel.setMoreMoviesView(
                                        response = { page -> sharedMediaViewModel.getPopularMovies(page) },
                                        pageLimit = 1
                                    )
                                } else {
                                    sharedMediaViewModel.setMoreSerialsView(
                                        response = { page -> sharedMediaViewModel.getPopularSeries(page)},
                                        pageLimit = 1
                                    )
                                }
                            },
                            seeAllForYouMedia = { paginatedRecommendedMedia ->
                                sharedMediaViewModel.setMoreUnifiedView(paginatedRecommendedMedia)
                            },
                            navController = screensNavController,
                            addToFavorite = { favoriteMedia ->
                                userViewModel.addFavoriteMedia(favoriteMedia)
                            },
                            isFavoriteUnit = { favoriteMedia ->
                                user?.favoriteMediaList?.find { it.id == favoriteMedia.id } != null
                            },
                            homeViewModel = homeViewModel
                        )
                    }

                }

                exploreScreen(
                    navController = screensNavController,
                    showBottomBar = { show -> showBottomBar = show},
                    seeAllMedia = { type, query ->
                        if (type == MediaCategories.MOVIE) {
                            screensNavController.navigate(MoreMoviesScreenRoute("Searched Movies"))
                            sharedMediaViewModel.setMoreMoviesView(
                                response = { page -> sharedMediaViewModel.searchMovies(query, page) }
                            )
                        } else {
                            screensNavController.navigate(MoreSerialsScreenRoute("Searched Series"))
                            sharedMediaViewModel.setMoreSerialsView(
                                response = { page -> sharedMediaViewModel.searchSeries(query, page) }
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
                    viewModelStoreOwner = viewModelStoreOwner
                )

                composable<FavoriteRoute> {
                    showBottomBar = true

                    val searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel(viewModelStoreOwner)

                    val searchedUnifiedMedia by searchHistoryViewModel.searchedUnifiedMedia.collectAsStateWithLifecycle()
                    val searchedMediaInDb by searchHistoryViewModel.searchedHistoryMedia.collectAsStateWithLifecycle()
                    val user by userViewModel.user.collectAsStateWithLifecycle()

                    val searchedHistory = searchedUnifiedMedia.sortedByDescending { unifiedMedia ->
                        searchedMediaInDb.find { mediaFromDb -> unifiedMedia.id == mediaFromDb.mediaId }?.viewedDateMillis
                    }.take(10)

                    user?.let { checkedUser ->
                        FavoriteNavigateScreen(
                            searchedHistoryFlow = searchedHistory,
                            searchedHistoryInDb = searchedMediaInDb,
                            user = checkedUser,
                            selectMedia = { unifiedMedia ->
                                if (unifiedMedia.mediaCategory == MediaCategories.MOVIE) {
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
                    showBottomBar = { show -> showBottomBar = show},
                    logOut = { userViewModel.logOut() }
                )

                composable<MoreMoviesScreenRoute> { backstackEntry ->
                    showBottomBar = true

                    val category = backstackEntry.arguments?.getString("category").toString()

                    val moreMoviesView = sharedMediaViewModel.moreMoviesView.collectAsLazyPagingItems()

                    MoreMoviesScreen(
                        turnBack = turnBack,
                        selectMovie = selectMovie,
                        category = category,
                        moreMoviesView = moreMoviesView,
                    )
                }

                composable<MoreSerialsScreenRoute> { backstackEntry ->
                    showBottomBar = true

                    val category = backstackEntry.arguments?.getString("category").toString()

                    val moreSeriesView = sharedMediaViewModel.moreSeriesView.collectAsLazyPagingItems()

                    MoreSerialsScreen(
                        turnBack = turnBack,
                        selectSeries = selectSeries,
                        category = category,
                        moreSeriesView = moreSeriesView,
                    )
                }

                composable<MoreUnifiedMediaScreenRoute> { backstackEntry ->
                    showBottomBar = true

                    val category = backstackEntry.arguments?.getString("category").toString()

                    val moreUnifiedMediaView = sharedMediaViewModel.moreUnifiedView.collectAsLazyPagingItems()

                    MoreUnifiedScreen(
                        turnBack = turnBack,
                        selectMedia = { id,mediaCategory ->
                            if(mediaCategory == MediaCategories.MOVIE) {
                                selectMovie(id)
                            } else {
                                selectSeries(id)
                            }
                        },
                        category = category,
                        moreUnifiedMediaView = moreUnifiedMediaView
                    )
                }

                composable(
                    route = "movie_details/{movie}",
                    arguments = listOf(navArgument("movie") { type = DetailedMovieNavType() }),
                ) { route ->
                    showBottomBar = false

                    val user by userViewModel.user.collectAsStateWithLifecycle()

                    val movieResponse = route.arguments?.getParcelable<DetailedMovieResponse>("movie")
                    movieResponse?.let {
                        MovieDetailsScreen(
                            navController = screensNavController,
                            movieResponse = movieResponse,
                            selectMovie = selectMovie,
                            addToFavorite = { favoriteMedia ->
                                userViewModel.addFavoriteMedia(favoriteMedia)
                            },
                            isFavoriteUnit = { favoriteMedia ->
                                user?.favoriteMediaList?.find { it.id == favoriteMedia.id } != null
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

                    val user by userViewModel.user.collectAsStateWithLifecycle()

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
                                user?.favoriteMediaList?.find { it.id == favoriteMedia.id } != null
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
        NoConnectionScreen()
    }
}