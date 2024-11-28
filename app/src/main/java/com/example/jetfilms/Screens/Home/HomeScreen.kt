package com.example.jetfilms.Screens.Home

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.jetfilms.Components.Gradient.GradientIcon
import com.example.jetfilms.Components.Buttons.TextButton
import com.example.jetfilms.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.Components.DetailedMediaComponents.DisplayRating
import com.example.jetfilms.Components.Lists.MoviesCategoryList
import com.example.jetfilms.Components.Lists.SerialsCategoryList
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.ViewModels.SeriesViewModel
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.states.rememberForeverLazyListState
import com.example.jetfilms.states.rememberForeverScrollState
import com.example.jetfilms.ui.theme.buttonsColor1
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(
    selectMovie: (movie: SimplifiedMovieDataClass) -> Unit,
    selectSeries: (serial: SimplifiedSerialObject) -> Unit,
    navController: NavController,
    moviesViewModel: MoviesViewModel,
    seriesViewModel: SeriesViewModel
) {

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val screenScrollState = rememberForeverScrollState(key = "Home screen")

    val scope = rememberCoroutineScope()

    val topRatedMovies = moviesViewModel.topRatedMovies.collectAsStateWithLifecycle()
    val popularMovies = moviesViewModel.popularMovies.collectAsStateWithLifecycle()
    val selectedMovie = moviesViewModel.selectedMovie.collectAsStateWithLifecycle()

    val popularSerials = seriesViewModel.popularSerials.collectAsStateWithLifecycle()

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
                    model = "$BASE_IMAGE_API_URL${selectedMovie.value?.posterUrl}",
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

                    DisplayRating(selectedMovie.value?.rating?:0f)

                    Text(
                        text = selectedMovie.value?.title.toString(),
                        style = typography.titleLarge,
                        fontSize = 26f.ssp,
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.sdp),
                        modifier = Modifier.padding(top = 18.sdp, start = 3.sdp, bottom = 6.sdp)
                    ){
                        TextButton(
                            onClick = {
                                selectedMovie.value?.let {
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
                                    if (false) colors.secondary.copy(.85f)
                                    else colors.secondary.copy(.92f)
                                )
                        ){
                            if(false){
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
                    scope.launch {
                        moviesViewModel.setSelectedMovie(movie.id)
                    }
                },
                moviesList = topRatedMovies.value.take(6),
                movieToSelect = selectedMovie.value
                )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.sdp),
                modifier = Modifier
                    .padding(top = 17.sdp)
            ) {
                items(topRatedMovies.value.take(6)) { movie ->
                    Box(
                        modifier = Modifier
                            .width(21.sdp)
                            .height(5.sdp)
                            .clip(CircleShape)
                            .background(
                                if (movie.id == selectedMovie.value?.id) buttonsColor1
                                else Color.LightGray.copy(0.68f)
                            )
                    )
                }
            }

            MoviesCategoryList(
                category = "Popular movies",
                selectMovie = selectMovie,
                moviesList = popularMovies.value.take(6),
                navController = navController,
                onSeeAllClick = {
                    moviesViewModel.setMoreMoviesView(
                        response = { page -> moviesViewModel.getPopularMovies(1) },
                        pageLimit = 1)
                },
                topPadding = 20.sdp,
                imageModifier = Modifier
                    .clip(RoundedCornerShape(6.sdp))
                    .width(126.sdp)
                    .height(200.sdp)
            )

            SerialsCategoryList(
                category = "Popular serials",
                selectSerial = selectSeries,
                serialsList = popularSerials.value.take(6),
                navController = navController,
                onSeeAllClick = {
                    popularSerials.value.let {
                        seriesViewModel.setMoreSerialsView(
                            response = {page -> seriesViewModel.getPopularSerials(1)}
                        )
                    }
                 //   moviesViewModel.setMoreMoviesView(popularMovies.value)
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
    val lazyListState = rememberForeverLazyListState("movies pager")
    val firstVisibleItemIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }

    val screenWidth = LocalConfiguration.current.screenWidthDp.sdp
    val screenHeight = LocalConfiguration.current.screenHeightDp.sdp
    val isVerticalOrientation = screenHeight > screenWidth

    val horizontalPadding = 8.sdp
    val lazyRowWidth = 474.sdp
    val itemsSpacing = 7.sdp
    val itemWidth = (lazyRowWidth + horizontalPadding - itemsSpacing * moviesList.size) / moviesList.size

    LazyRow(
        state = lazyListState,
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(itemsSpacing),
        modifier = Modifier
            .padding(
                top = 20.sdp,
                start = if (isVerticalOrientation) horizontalPadding else 0.sdp,
            )
            .width(if (isVerticalOrientation) screenWidth else lazyRowWidth)
    ) {

        items(Int.MAX_VALUE){ index ->
            if(moviesList.isNotEmpty()){

                if(firstVisibleItemIndex + 5 >= index) {
                    val movie = moviesList[index % moviesList.size]
                    Box(
                        modifier = Modifier
                            .width(itemWidth)
                            .height(46.sdp)
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
}

