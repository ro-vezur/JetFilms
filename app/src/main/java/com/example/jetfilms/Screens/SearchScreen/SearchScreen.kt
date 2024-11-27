package com.example.jetfilms.Screens.SearchScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Components.Cards.MovieCard
import com.example.jetfilms.Components.Cards.SerialCard
import com.example.jetfilms.Components.Cards.UnifiedCard
import com.example.jetfilms.Components.InputFields.SearchField
import com.example.jetfilms.Components.Lists.MoviesCategoryList
import com.example.jetfilms.Components.Lists.SerialsCategoryList
import com.example.jetfilms.Components.TopBars.FiltersTopBar
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Helpers.navigate.navigateToSelectedMovie
import com.example.jetfilms.Helpers.navigate.navigateToSelectedSerial
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SearchScreen(
    selectMovie: (movie: SimplifiedMovieDataClass) -> Unit,
    selectSeries: (series: SimplifiedSerialObject) -> Unit,
    navController: NavController,
    moviesViewModel: MoviesViewModel
) {
    val hazeState = remember { HazeState() }
    val scrollState = rememberScrollState()

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val scope = rememberCoroutineScope()

    var searchText = moviesViewModel.searchText.collectAsStateWithLifecycle()
    var requestSent = moviesViewModel.requestSent.collectAsStateWithLifecycle()

    val searchedMovies = moviesViewModel.searchedMovies.collectAsStateWithLifecycle()
    val searchedSerials = moviesViewModel.searchedSerials.collectAsStateWithLifecycle()

    val selectedSort = moviesViewModel.selectedSort.collectAsStateWithLifecycle()
    val filteredUnifiedData = moviesViewModel.filteredUnifiedData.collectAsLazyPagingItems()

    var searchBarXOffset by remember { mutableStateOf(0) }
    val searchBarHeight = 52.sdp
    val columnItemsSpacing = 15

    var showFilteredResults = moviesViewModel.showFilteredResults.collectAsStateWithLifecycle()

    LaunchedEffect(requestSent) {
        searchBarXOffset = if (requestSent.value) screenWidth / 50 else screenWidth / 27
    }


    Box(
        modifier = Modifier
    ) {
        AnimatedVisibility(visible = !showFilteredResults.value) {
            Scaffold(
                containerColor = primaryColor,
                topBar = {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(searchBarHeight)

                            .hazeChild(state = hazeState)
                            .offset(animateIntAsState(targetValue = searchBarXOffset).value.sdp)

                    ) {
                        SearchField(
                            text = searchText.value,
                            onTextChange = {
                                moviesViewModel.setSearchText(it)
                            },
                            onSearchClick = {
                                moviesViewModel.setIsRequestSent(true)
                                moviesViewModel.setSearchedMovies(searchText.value)
                                moviesViewModel.setSearchedSerials(searchText.value)
                                // moviesViewModel.setSearchedData(searchText.value)

                                // searchBarXOffset = screenWidth / 50

                            },
                            clearText = {
                                moviesViewModel.setSearchText("")
                            },
                            cancelRequest = {
                                moviesViewModel.setIsRequestSent(false)
                                moviesViewModel.setSearchedMovies(null)
                                moviesViewModel.setSearchedSerials(null)
                                //  moviesViewModel.setSearchedData("")

                                //  searchBarXOffset = screenWidth / 27
                            },
                            requestSent = requestSent.value
                        )
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 7.sdp)
                        .fillMaxSize()
                        .haze(
                            hazeState,
                            backgroundColor = hazeStateBlurBackground,
                            tint = hazeStateBlurTint,
                            blurRadius = HAZE_STATE_BLUR.sdp,
                        )
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(columnItemsSpacing.sdp),
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        Spacer(modifier = Modifier.height(searchBarHeight))

                        searchedMovies.value?.let {
                            MoviesCategoryList(
                                category = "Searched Movies",
                                selectMovie = selectMovie,
                                moviesList = it.results,
                                navController = navController,
                                onSeeAllClick = {
                                    moviesViewModel.setMoreMoviesView(
                                        response = { page ->
                                            moviesViewModel.searchMovies(searchText.value, page)
                                        }
                                    )
                                },
                                showSeeAllButton = it.totalResults > PAGE_SIZE,
                                imageModifier = Modifier
                                    .clip(RoundedCornerShape(6.sdp))
                                    .width(114.sdp)
                                    .height(185.sdp)
                            )
                        }

                        searchedSerials.value?.let {
                            SerialsCategoryList(
                                category = "Searched Serials",
                                selectSerial = selectSeries,
                                serialsList = it.results,
                                navController = navController,
                                onSeeAllClick = {
                                    moviesViewModel.setMoreSerialsView(
                                        response = { page ->
                                            moviesViewModel.searchSerials(searchText.value, page)
                                        }
                                    )
                                },
                                showSeeAllButton = it.totalResults > PAGE_SIZE,
                                imageModifier = Modifier
                                    .clip(RoundedCornerShape(6.sdp))
                                    .width(114.sdp)
                                    .height(185.sdp)
                            )
                        }

                        Spacer(modifier = Modifier.height((BOTTOM_NAVIGATION_BAR_HEIGHT).sdp))
                    }
                }
            }
        }

        AnimatedVisibility(visible = showFilteredResults.value) {
            Scaffold(
                containerColor = primaryColor,
                topBar = {
                    FiltersTopBar(
                        turnBack = { moviesViewModel.showFilteredData( false) },
                        text = "Discover",
                        reset = {
                            moviesViewModel.setSelectedSort(null)
                            moviesViewModel.showFilteredData( false)
                                },
                        hazeState = hazeState
                    )
                }
            ) { innerPadding ->
                when {
                    filteredUnifiedData.loadState.refresh is LoadState.Loading -> {
                        CircularProgressIndicator()
                    }

                    else -> LazyVerticalGrid(
                        modifier = Modifier
                            .padding(horizontal = 6.sdp)
                            .fillMaxSize()
                            .haze(
                                hazeState,
                                backgroundColor = hazeStateBlurBackground,
                                tint = hazeStateBlurTint,
                                blurRadius = HAZE_STATE_BLUR.sdp,
                            ),
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(7.sdp),
                        verticalArrangement = Arrangement.spacedBy(8.sdp),
                        contentPadding = PaddingValues(horizontal = 0.sdp),
                        userScrollEnabled = true,
                    ) {

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Spacer(
                                modifier = Modifier.height(
                                    searchBarHeight
                                )
                            )
                        }

                        items(filteredUnifiedData.itemCount) { index ->
                            val searchedObject = filteredUnifiedData[index]

                            searchedObject?.let {
                                UnifiedCard(
                                    unifiedMedia = searchedObject,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.sdp))
                                        .height(155.sdp)
                                        .clickable {
                                            scope.launch {
                                                if (searchedObject.mediaType == MediaFormats.MOVIE) {
                                                    moviesViewModel
                                                        .getMovie(searchedObject.id)
                                                        ?.let {
                                                            navigateToSelectedMovie(
                                                                navController,
                                                                it
                                                            )
                                                        }
                                                } else if(searchedObject.mediaType == MediaFormats.SERIES) {
                                                    moviesViewModel
                                                        .getSerial(searchedObject.id)
                                                        .let {
                                                            navigateToSelectedSerial(
                                                                navController,
                                                                it
                                                            )
                                                        }
                                                }
                                            }
                                        }
                                )
                            }
                        }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Spacer(modifier = Modifier.height(BOTTOM_NAVIGATION_BAR_HEIGHT.sdp))
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = !requestSent.value,
            enter = fadeIn(animationSpec = tween(320)),
            exit = fadeOut(animationSpec = tween(320)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 25).sdp)
                    .width(90.sdp)
                    .height(30.sdp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        if (selectedSort.value == null) {
                            navController.navigate("FilterScreen")
                        } else {
                            navController.navigate("FilterScreen")
                            moviesViewModel.setSelectedSort(selectedSort.value)
                            moviesViewModel.showFilteredData( true)
                        }
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    tint = primaryColor,
                    contentDescription = "filter",
                    modifier = Modifier
                        .padding(start = 10.sdp)
                        .size(22.sdp)
                )

                Text(
                    text = "Filters",
                    color = primaryColor,
                    fontSize = 14.ssp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 4.sdp)
                )
            }
        }
    }
}


/*
           LazyVerticalGrid(
               modifier = Modifier
                   //  .padding(innerPadding)
                   .fillMaxSize()
                   .haze(
                       hazeState,
                       backgroundColor = hazeStateBlurBackground,
                       tint = hazeStateBlurTint,
                       blurRadius = HAZE_STATE_BLUR.sdp,
                   ),
               columns = GridCells.Fixed(3),
               horizontalArrangement = Arrangement.spacedBy(7.sdp),
               verticalArrangement = Arrangement.spacedBy(8.sdp),
               contentPadding = PaddingValues(horizontal = 0.sdp),
               userScrollEnabled = true,
           ) {

               item(span = { GridItemSpan(maxLineSpan) }) { Spacer(modifier = Modifier.height(searchBarHeight))}

               if (searchedData.value.isNotEmpty()) {
                   items(searchedData.value) { searchedObject ->
                       if (searchedObject.type == MediaFormats.MOVIE && searchedObject.moviesResponse != null) {
                           MovieCard(
                               movie = searchedObject.moviesResponse,
                               modifier = Modifier
                                   .clip(RoundedCornerShape(8.sdp))
                                   .height(155.sdp)
                                   .clickable {
                                       scope.launch {
                                           moviesViewModel
                                               .getMovie(searchedObject.moviesResponse.id)
                                               ?.let {
                                                   navigateToSelectedMovie(navController, it)
                                               }
                                       }
                                   }
                           )
                       }
                       else if(searchedObject.type == MediaFormats.SERIES && searchedObject.seriesResponse != null){
                           SerialCard(
                               serial = searchedObject.seriesResponse,
                               modifier = Modifier
                                   .clip(RoundedCornerShape(8.sdp))
                                   .height(155.sdp)
                                   .clickable {
                                       scope.launch {
                                           moviesViewModel
                                               .getSerial(searchedObject.seriesResponse.id)
                                               ?.let {
                                                   navigateToSelectedSerial(navController, it)
                                               }
                                       }
                                   }
                           )
                       }
                   }
               }

               item(span = { GridItemSpan(maxLineSpan) }) {
                   Spacer(modifier = Modifier.height(BOTTOM_NAVIGATION_BAR_HEIGHT.sdp))
               }
           }
           */