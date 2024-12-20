package com.example.jetfilms.View.Screens.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetfilms.Helpers.navigate.navigateToSelectedMovie
import com.example.jetfilms.View.Components.Gradient.GradientIcon
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.Helpers.DTOsConverters.ToFavoriteMedia.MovieDataToFavoriteMedia
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.View.Components.DetailedMediaComponents.DisplayRating
import com.example.jetfilms.View.Components.DetailedMediaComponents.MediaTitle
import com.example.jetfilms.View.Components.Lists.MoviesCategoryList
import com.example.jetfilms.View.Components.Lists.SerialsCategoryList
import com.example.jetfilms.View.Components.Lists.UnifiedMediaCategoryList
import com.example.jetfilms.View.Screens.MoreMoviesScreenRoute
import com.example.jetfilms.View.Screens.MoreSerialsScreenRoute
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.View.states.rememberForeverLazyListState
import com.example.jetfilms.View.states.rememberForeverScrollState
import com.example.jetfilms.ViewModels.HomeViewModel
import com.example.jetfilms.ui.theme.buttonsColor1
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(
    navController: NavController,
    selectMovie: (id: Int) -> Unit,
    selectSeries: (id: Int) -> Unit,
    seeAllMedia: (category: MediaCategories) -> Unit,
    addToFavorite: (favoriteMedia: FavoriteMedia) -> Unit,
    isFavoriteUnit: (favoriteMedia: FavoriteMedia) -> Boolean,
    homeViewModel: HomeViewModel,
) {

    val colors = MaterialTheme.colorScheme

    val screenScrollState = rememberForeverScrollState(key = "Home screen")

    val topRatedMovies by homeViewModel.topRatedMovies.collectAsStateWithLifecycle()
    val popularMovies by homeViewModel.popularMovies.collectAsStateWithLifecycle()
    val recommendedMedia by homeViewModel.recommendedMedia.collectAsStateWithLifecycle()
    val selectedMovie by homeViewModel.selectedMovie.collectAsStateWithLifecycle()

    val popularSerials = homeViewModel.popularSerials.collectAsStateWithLifecycle()

    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(selectedMovie) {
        selectedMovie?.let {
            isFavorite = isFavoriteUnit(MovieDataToFavoriteMedia(it))
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primary)
            .verticalScroll(screenScrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.sdp)
        ) {
            AsyncImage(
                model = "$BASE_IMAGE_API_URL${selectedMovie?.posterUrl}",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                colors.primary.copy(0.64f),
                                colors.primary.copy(1f),
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 15.sdp)
            ) {

                DisplayRating(selectedMovie?.rating?:0f)

                MediaTitle(text = selectedMovie?.title.toString())

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.sdp),
                    modifier = Modifier.padding(top = 18.sdp, start = 3.sdp, bottom = 6.sdp)
                ){
                    TextButton(
                        onClick = {
                            selectedMovie?.let {
                                navigateToSelectedMovie(navController,it)
                            }
                        },
                        gradient = blueHorizontalGradient,
                        width = 138.sdp,
                        height = 36.sdp,
                        corners = RoundedCornerShape(18.sdp),
                        textAlign = Alignment.Center,
                        text = "Watch Now",
                        modifier = Modifier
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(start = 6.sdp)
                            .size(34.sdp)
                            .clip(CircleShape)
                            .background(
                                if (isFavorite) colors.secondary.copy(.85f)
                                else colors.secondary.copy(.92f)
                            )
                            .clickable {
                                isFavorite = !isFavorite
                                selectedMovie?.let {
                                    addToFavorite(MovieDataToFavoriteMedia(it))
                                }

                            }
                    ){
                        if(isFavorite){
                            GradientIcon(
                                icon = Icons.Filled.Bookmarks,
                                contentDescription = "favorite",
                                gradient = blueHorizontalGradient,
                                modifier = Modifier
                                    .size(21.sdp)
                            )
                        } else{
                            Icon(
                                imageVector = Icons.Outlined.Bookmarks,
                                contentDescription = "unfavorable",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(21.sdp)
                            )
                        }
                    }
                }
            }
        }

        MoviePager(
            selectMovie = { movie ->
                homeViewModel.setSelectedMovie(movie.id)
            },
            moviesList = topRatedMovies.take(6),
            movieToSelect = selectedMovie
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.sdp),
            modifier = Modifier
                .padding(top = 17.sdp)
        ) {
            items(topRatedMovies.take(6)) { movie ->
                Box(
                    modifier = Modifier
                        .width(21.sdp)
                        .height(5.sdp)
                        .clip(CircleShape)
                        .background(
                            if (movie.id == selectedMovie?.id) buttonsColor1
                            else Color.LightGray.copy(0.68f)
                        )
                )
            }
        }

        UnifiedMediaCategoryList(
            category = "For You",
            selectMedia = { id, category ->
                if(category == MediaCategories.MOVIE) {
                    selectMovie(id)
                } else {
                    selectMovie(id)
                }
            },
            unifiedMediaList = recommendedMedia.take(6),
            onSeeAllClick = {
                navController.navigate(MoreMoviesScreenRoute("Popular movies"))
                seeAllMedia(MediaCategories.MOVIE)
            },
            topPadding = 20.sdp,
            imageModifier = Modifier
                .clip(RoundedCornerShape(6.sdp))
                .width(126.sdp)
                .height(200.sdp)
        )

        MoviesCategoryList(
            category = "Popular movies",
            selectMovie = selectMovie,
            moviesList = popularMovies.take(6),
            onSeeAllClick = {
                navController.navigate(MoreMoviesScreenRoute("Popular movies"))
                seeAllMedia(MediaCategories.MOVIE)
            },
            topPadding = 20.sdp,
            imageModifier = Modifier
                .clip(RoundedCornerShape(6.sdp))
                .width(126.sdp)
                .height(200.sdp)
        )

        SerialsCategoryList(
            category = "Popular series",
            selectSerial = selectSeries,
            serialsList = popularSerials.value.take(6),
            onSeeAllClick = {
                navController.navigate(MoreSerialsScreenRoute("Popular series"))
                seeAllMedia(MediaCategories.SERIES)
            },
            topPadding = 20.sdp,
            bottomPadding = 62.sdp,
            imageModifier = Modifier
                .clip(RoundedCornerShape(6.sdp))
                .width(126.sdp)
                .height(200.sdp)
        )
    }
}

@Composable
private fun MoviePager(
    selectMovie:(movie: SimplifiedMovieDataClass) -> Unit,
    moviesList: List<SimplifiedMovieDataClass>,
    movieToSelect: DetailedMovieResponse?
) {

    val scope = rememberCoroutineScope()
    val lazyListState = rememberForeverLazyListState(
        "movies pager",
    )
    val firstVisibleItemIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }

    val itemsSpacing = 7.sdp
    val itemWidth = 70.sdp

    LazyRow(
        state = lazyListState,
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(itemsSpacing),
        modifier = Modifier
            .padding(
                top = 20.sdp,
            )
            .fillMaxWidth()
    ) {
        items(Int.MAX_VALUE){ index ->
            if(moviesList.isNotEmpty() && firstVisibleItemIndex + 5 >= index){
                val movie = moviesList[index % moviesList.size]
                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .height(50.sdp)
                        .clip(RoundedCornerShape(7.sdp))
                ) {

                    AsyncImage(
                        model = "$BASE_IMAGE_API_URL${movie.poster}",
                        contentDescription = "movie poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                selectMovie(moviesList[index % moviesList.size])
                                scope.launch {
                                    lazyListState.animateScrollToItem(index)
                                }
                            }
                    )

                    if (movieToSelect?.id != moviesList[index % moviesList.size].id) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Color.Black.copy(0.55f)
                                )
                        )
                    }
                }
            }
        }
    }
}

