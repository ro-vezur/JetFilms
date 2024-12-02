package com.example.jetfilms.Screens.SearchScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
import com.example.jetfilms.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Helpers.DTOsConverters.MovieDataToUnifiedMedia
import com.example.jetfilms.Helpers.DTOsConverters.SeriesDataToUnifiedMedia
import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Helpers.navigate.navigateToSelectedMovie
import com.example.jetfilms.Helpers.navigate.navigateToSelectedSerial
import com.example.jetfilms.Helpers.removeNumbersAfterDecimal
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.R
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ViewModels.SeriesViewModel
import com.example.jetfilms.ViewModels.UnifiedMediaViewModel
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.secondaryColor
import com.example.jetfilms.ui.theme.whiteColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SearchScreen(
    selectMovie: (id: Int) -> Unit,
    selectSeries: (id: Int) -> Unit,
    navController: NavController,
    moviesViewModel: MoviesViewModel,
    seriesViewModel: SeriesViewModel,
    unifiedMediaViewModel: UnifiedMediaViewModel,
    searchHistoryViewModel: SearchHistoryViewModel,
) {
    val hazeState = remember { HazeState() }
    val scrollState = rememberScrollState()

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val scope = rememberCoroutineScope()

    var searchText = unifiedMediaViewModel.searchText.collectAsStateWithLifecycle()
    var requestSent = unifiedMediaViewModel.requestSent.collectAsStateWithLifecycle()

    val searchedMovies = moviesViewModel.searchedMovies.collectAsStateWithLifecycle()
    val searchedSerials = seriesViewModel.searchedSerials.collectAsStateWithLifecycle()

    val selectedSort = unifiedMediaViewModel.selectedSort.collectAsStateWithLifecycle()
    val filteredUnifiedData = unifiedMediaViewModel.filteredUnifiedData.collectAsLazyPagingItems()
    val showFilteredResults = unifiedMediaViewModel.showFilteredResults.collectAsStateWithLifecycle()

    val searchHistory = searchHistoryViewModel.searchedHistory.collectAsStateWithLifecycle()
    val searchSuggestions = unifiedMediaViewModel.searchSuggestions.collectAsStateWithLifecycle()

    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val isSearchBarFocused by interactionSource.collectIsFocusedAsState()

    var searchBarXOffset by remember { mutableStateOf(0) }
    val searchBarHeight = 52.sdp
    val columnItemsSpacing = 15



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
                                unifiedMediaViewModel.setSearchText(it)
                                unifiedMediaViewModel.fetchSearchSuggestions(it)
                            },
                            onSearchClick = {
                                unifiedMediaViewModel.setIsRequestSent(true)
                                moviesViewModel.setSearchedMovies(searchText.value)
                                seriesViewModel.setSearchedSerials(searchText.value)
                            },
                            clearText = {
                                unifiedMediaViewModel.setSearchText("")
                                unifiedMediaViewModel.fetchSearchSuggestions("")
                            },
                            cancelRequest = {
                                unifiedMediaViewModel.setIsRequestSent(false)
                                moviesViewModel.setSearchedMovies(null)
                                seriesViewModel.setSearchedSerials(null)
                            },
                            requestSent = requestSent.value,
                            interactionSource = interactionSource,
                        )
                    }
                }
            ) { innerPadding ->

                Box(
                    modifier = Modifier
                    .fillMaxSize()
                ){
                    if (requestSent.value) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(columnItemsSpacing.sdp),
                            modifier = Modifier
                                .padding(horizontal = 7.sdp)
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                                .haze(
                                    hazeState,
                                    backgroundColor = hazeStateBlurBackground,
                                    tint = hazeStateBlurTint,
                                    blurRadius = HAZE_STATE_BLUR.sdp,
                                )
                        ) {
                            Spacer(modifier = Modifier.height(searchBarHeight))

                            searchedMovies.value?.let {
                                MoviesCategoryList(
                                    category = "Searched Movies",
                                    selectMovie = { id ->
                                        scope.launch {
                                            selectMovie(id)
                                            val searchedMovie = moviesViewModel.getMovie(id)

                                            searchedMovie?.let {
                                                searchHistoryViewModel.insertSearchedMedia(
                                                    SearchedMedia(
                                                        mediaId = searchedMovie.id,
                                                        mediaType = MediaFormats.MOVIE.format,
                                                        viewedDate = DateFormats.getCurrentDate()
                                                    )
                                                )

                                                searchHistoryViewModel.addSearchHistoryMedia(
                                                    MovieDataToUnifiedMedia(searchedMovie)
                                                )
                                            }

                                        }
                                    },
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
                                    selectSerial = { id ->
                                        selectSeries(id)
                                        scope.launch {
                                            val searchedSeries = seriesViewModel.getSerial(id)

                                            searchHistoryViewModel.insertSearchedMedia(
                                                SearchedMedia(
                                                    mediaId = searchedSeries.id,
                                                    mediaType = MediaFormats.SERIES.format,
                                                    viewedDate = DateFormats.getCurrentDate()
                                                )
                                            )

                                            searchHistoryViewModel.addSearchHistoryMedia(
                                                SeriesDataToUnifiedMedia(searchedSeries)
                                            )
                                        }
                                    },
                                    serialsList = it.results,
                                    navController = navController,
                                    onSeeAllClick = {
                                        seriesViewModel.setMoreSerialsView(
                                            response = { page ->
                                                seriesViewModel.searchSerials(
                                                    searchText.value,
                                                    page
                                                )
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
                    } else {
                        LazyVerticalGrid(
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

                            items(searchHistory.value) { searchedMedia ->

                                UnifiedCard(
                                    unifiedMedia = searchedMedia,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.sdp))
                                        .height(155.sdp)
                                        .clickable {
                                            scope.launch {
                                                if (searchedMedia.mediaType == MediaFormats.MOVIE) {
                                                    selectMovie(searchedMedia.id)
                                                } else {
                                                    selectSeries(searchedMedia.id)
                                                }
                                            }
                                        }
                                )
                            }

                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Spacer(modifier = Modifier.height(BOTTOM_NAVIGATION_BAR_HEIGHT.sdp))
                            }
                        }
                    }

                    if(isSearchBarFocused){
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(4.sdp),
                            modifier = Modifier
                                .padding(top = (FILTER_TOP_BAR_HEIGHT + 12).sdp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.sdp))
                                .background(primaryColor)
                        ) {
                            Log.d("suggestions", searchSuggestions.value.toString())

                            items(searchSuggestions.value) { suggestion ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(vertical = 5.sdp)
                                        .fillMaxWidth(0.96f)
                                        .heightIn(25.sdp, 50.sdp)
                                        .clickable {
                                            focusManager.clearFocus()
                                            unifiedMediaViewModel.setSearchText(suggestion.name)
                                            unifiedMediaViewModel.setIsRequestSent(true)
                                            moviesViewModel.setSearchedMovies(suggestion.name)
                                            seriesViewModel.setSearchedSerials(suggestion.name)
                                        }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Search",
                                        tint = whiteColor,
                                        modifier = Modifier
                                            .padding(start = 7.sdp)
                                            .size(22.sdp)
                                    )

                                    Text(
                                        text = suggestion.name,
                                        color = whiteColor,
                                        fontSize = 13.ssp,
                                        lineHeight = 17.ssp,
                                        modifier = Modifier
                                            .padding(start = 7.sdp)
                                            .fillMaxWidth(0.75f)
                                    )

                                    Image(
                                        painter = painterResource(id = R.drawable.imdb_logo_2016_svg),
                                        contentDescription = "imdb",
                                        modifier = Modifier
                                            .padding(start = 10.sdp)
                                            .width(22.sdp)
                                    )

                                    Text(
                                        text = removeNumbersAfterDecimal(
                                            suggestion.rating,
                                            2
                                        ).toString(),
                                        color = whiteColor,
                                        fontSize = 12.ssp,
                                        lineHeight = 17.ssp,
                                        modifier = Modifier
                                            .padding(start = 8.sdp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }

        AnimatedVisibility(visible = showFilteredResults.value) {
            Scaffold(
                containerColor = primaryColor,
                topBar = {
                    FiltersTopBar(
                        turnBack = { unifiedMediaViewModel.showFilteredData( false) },
                        text = "Discover",
                        reset = {
                            unifiedMediaViewModel.setSelectedSort(null)
                            unifiedMediaViewModel.showFilteredData( false)
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
                                                } else if (searchedObject.mediaType == MediaFormats.SERIES) {
                                                    seriesViewModel
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
                            unifiedMediaViewModel.setSelectedSort(selectedSort.value)
                            unifiedMediaViewModel.showFilteredData(true)
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
