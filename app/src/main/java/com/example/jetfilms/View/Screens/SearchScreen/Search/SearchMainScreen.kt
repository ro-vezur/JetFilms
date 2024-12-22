package com.example.jetfilms.View.Screens.SearchScreen.Search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.View.Components.InputFields.SearchField
import com.example.jetfilms.View.Components.Lists.MoviesCategoryList
import com.example.jetfilms.View.Components.Lists.SerialsCategoryList
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.PAGE_SIZE
import com.example.jetfilms.View.Components.LazyComponents.LazyGrid.UnifiedMediaVerticalLazyGrid
import com.example.jetfilms.View.Screens.SearchScreen.SearchScreenComponents.FilterButton
import com.example.jetfilms.View.Screens.SearchScreen.SearchScreenComponents.SearchSuggestionsLazyColumn
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ViewModels.SearchViewModel
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
    selectMedia: (id: Int, type: MediaCategories) -> Unit,
    seeAllMedia: (type: MediaCategories, query: String) -> Unit,
    onFilterButtonClick: () -> Unit,
    searchHistoryViewModel: SearchHistoryViewModel,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val hazeState = remember { HazeState() }
    val scrollState = rememberScrollState()

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val scope = rememberCoroutineScope()

    val searchText by searchViewModel.searchText.collectAsStateWithLifecycle()
    val requestSent = searchViewModel.requestSent.collectAsStateWithLifecycle()

    val searchedMovies by searchViewModel.searchedMovies.collectAsStateWithLifecycle()
    val searchedSerials by searchViewModel.searchedSeries.collectAsStateWithLifecycle()
    val searchSuggestions by searchViewModel.searchSuggestions.collectAsStateWithLifecycle()

    val searchedHistoryUnifiedMedia by searchHistoryViewModel.searchedUnifiedMedia.collectAsStateWithLifecycle()
    val searchedHistorySearchedMedia by searchHistoryViewModel.searchedHistoryMedia.collectAsStateWithLifecycle()


    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val isSearchBarFocused by interactionSource.collectIsFocusedAsState()

    var searchBarXOffset by remember { mutableStateOf(0) }
    val searchBarHeight = 52.sdp
    val lazyColumnsHorizontalPadding = 7.sdp
    val columnItemsSpacing = 15

    LaunchedEffect(requestSent) {
        searchBarXOffset = if (requestSent.value) screenWidth / 50 else screenWidth / 27
    }

    Box(
        modifier = Modifier
    ) {
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
                            text = searchText,
                            onTextChange = {
                                searchViewModel.setSearchText(it)
                                searchViewModel.fetchSearchSuggestions(it)
                            },
                            onSearchClick = {
                                searchViewModel.setIsRequestSent(true)
                                searchViewModel.setSearchedMovies(searchText)
                                searchViewModel.setSearchedSerials(searchText)
                            },
                            clearText = {
                                searchViewModel.setSearchText("")
                                searchViewModel.fetchSearchSuggestions("")
                            },
                            cancelRequest = {
                                searchViewModel.setIsRequestSent(false)
                                searchViewModel.setSearchedMovies(null)
                                searchViewModel.setSearchedSerials(null)
                            },
                            requestSent = requestSent.value,
                            interactionSource = interactionSource,
                        )
                    }
                }
            ) { _ ->

                Box(
                    modifier = Modifier
                    .fillMaxSize()
                ){
                    AnimatedVisibility( visible = requestSent.value) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(columnItemsSpacing.sdp),
                            modifier = Modifier
                                .padding(horizontal = lazyColumnsHorizontalPadding)
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

                            searchedMovies?.let {
                                MoviesCategoryList(
                                    category = "Searched Movies",
                                    selectMovie = { id ->
                                        scope.launch {
                                            searchHistoryViewModel.getMovie(id).let { searchedMovie ->
                                                val searchedMedia = SearchedMedia.fromDetailedMovieResponse(searchedMovie)

                                                searchHistoryViewModel.insertSearchedMediaToDb(searchedMedia )
                                                searchHistoryViewModel.addMovieToFlow(id)
                                                selectMedia(id,MediaCategories.MOVIE)
                                            }
                                        }
                                    },
                                    moviesList = it.results,
                                    onSeeAllClick = { seeAllMedia(MediaCategories.MOVIE,searchText) },
                                    showSeeAllButton = it.totalResults > PAGE_SIZE,
                                    imageModifier = Modifier
                                        .clip(RoundedCornerShape(6.sdp))
                                        .width(114.sdp)
                                        .height(185.sdp)
                                )
                            }

                            searchedSerials?.let {
                                SerialsCategoryList(
                                    category = "Searched Serials",
                                    selectSerial = { id ->
                                        scope.launch {
                                            val searchedSeries = searchHistoryViewModel.getSeries(id)
                                            val searchedMedia = SearchedMedia.fromDetailedSeriesResponse(searchedSeries)

                                            searchHistoryViewModel.insertSearchedMediaToDb(searchedMedia)
                                            searchHistoryViewModel.addSeriesToFlow(id)
                                            selectMedia(id,MediaCategories.SERIES)
                                        }
                                    },
                                    serialsList = it.results,
                                    onSeeAllClick = { seeAllMedia(MediaCategories.SERIES,searchText) },
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

                    AnimatedVisibility(visible = !requestSent.value){
                        UnifiedMediaVerticalLazyGrid(
                            modifier = Modifier
                                .padding(horizontal = lazyColumnsHorizontalPadding)
                                .fillMaxSize()
                                .haze(
                                    hazeState,
                                    backgroundColor = hazeStateBlurBackground,
                                    tint = hazeStateBlurTint,
                                    blurRadius = HAZE_STATE_BLUR.sdp,
                                ),
                            topPadding = searchBarHeight,
                            data = searchedHistoryUnifiedMedia.sortedByDescending { unifiedMedia ->
                                searchedHistorySearchedMedia.find {
                                    it.id == "${unifiedMedia.id}${unifiedMedia.mediaCategory.format}"
                                }?.viewedDateMillis
                            },
                            selectMedia = { unifiedMedia -> selectMedia(unifiedMedia.id,unifiedMedia.mediaCategory) }
                        )
                    }

                    AnimatedVisibility(
                        visible = isSearchBarFocused,
                        enter = fadeIn() ,
                        exit = fadeOut(),
                    ){
                        SearchSuggestionsLazyColumn(
                            searchSuggestions = searchSuggestions,
                            onSuggestionClick = { suggestion ->
                                focusManager.clearFocus()
                                searchViewModel.setSearchText(suggestion.title)
                                searchViewModel.setIsRequestSent(true)
                                searchViewModel.setSearchedMovies(suggestion.title)
                                searchViewModel.setSearchedSerials(suggestion.title)
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = !requestSent.value,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        FilterButton(
                            onClick = onFilterButtonClick
                        )
                    }
                }
            }



    }
}
