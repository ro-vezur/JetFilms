package com.example.jetfilms.Screens.SearchScreen

import android.util.Log
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Components.Cards.MovieCard
import com.example.jetfilms.Components.Cards.SerialCard
import com.example.jetfilms.Components.InputFields.SearchField
import com.example.jetfilms.Components.Lists.MoviesCategoryList
import com.example.jetfilms.Components.Lists.SerialsCategoryList
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Helpers.navigate.navigateToSelectedMovie
import com.example.jetfilms.Helpers.navigate.navigateToSelectedSerial
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    moviesViewModel: MoviesViewModel
) {
    val hazeState = remember{HazeState()}
    val scrollState = rememberScrollState()

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val scope = rememberCoroutineScope()

    var searchText = moviesViewModel.searchText.collectAsStateWithLifecycle()
    var requestSent = moviesViewModel.requestSent.collectAsStateWithLifecycle()
    val searchedMovies = moviesViewModel.searchedMovies.collectAsStateWithLifecycle()
    val searchedSerials = moviesViewModel.searchedSerials.collectAsStateWithLifecycle()

    var searchBarXOffset by remember{ mutableStateOf(0) }
    val searchBarHeight = 52.sdp
    val columnItemsSpacing = 15

    LaunchedEffect(requestSent) {
        searchBarXOffset = if(requestSent.value) screenWidth / 50 else screenWidth / 27
        Log.d("search bar x offset",searchBarXOffset.toString())
    }



    Scaffold(
        containerColor = primaryColor,
        topBar = {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(searchBarHeight)
                    .offset(animateIntAsState(targetValue = searchBarXOffset).value.sdp)
                    .hazeChild(state = hazeState)


            ){
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
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(columnItemsSpacing.sdp),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
              Spacer(modifier = Modifier.height(searchBarHeight))

                searchedMovies.value?.let { it ->
                    MoviesCategoryList(
                        category = "Searched Movies",
                        selectMovie = { movie ->
                            scope.launch {
                                moviesViewModel.getMovie(movie.id)?.let { checkedMovie ->
                                    navigateToSelectedMovie(navController, checkedMovie)
                                }
                            }
                        },
                        moviesList = it.results,
                        navController = navController,
                        onSeeAllClick = { moviesViewModel.setMoreMoviesView(response = {page -> moviesViewModel.searchMovies(searchText.value,page) }) },
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
                        selectSerial = { serial ->
                            scope.launch {
                                navigateToSelectedSerial(navController, moviesViewModel.getSerial(serial.id))

                            }
                        },
                        serialsList = it.results,
                        navController = navController,
                        onSeeAllClick = { moviesViewModel.setMoreSerialsView(response = {page -> moviesViewModel.searchSerials(searchText.value,page) }) },
                        showSeeAllButton = it.totalResults > PAGE_SIZE,
                        imageModifier = Modifier
                            .clip(RoundedCornerShape(6.sdp))
                            .width(114.sdp)
                            .height(185.sdp)
                    )
                }

                Spacer(modifier = Modifier.height((BOTTOM_NAVIGATION_BAR_HEIGHT).sdp))
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


        }
    }
}