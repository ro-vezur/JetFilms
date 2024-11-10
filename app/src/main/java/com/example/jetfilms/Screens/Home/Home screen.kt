package com.example.jetfilms.Screens.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetfilms.CustomComposables.GradientIcon
import com.example.jetfilms.CustomComposables.MovieCard
import com.example.jetfilms.CustomComposables.TextButton
import com.example.jetfilms.Data_Classes.DetailedMovieDataClass
import com.example.jetfilms.Data_Classes.SimplifiedMovieDataClass
import com.example.jetfilms.Data_Classes.detailedMoviesDataList
import com.example.jetfilms.Data_Classes.simplifiedMoviesDataList
import com.example.jetfilms.R
import com.example.jetfilms.Screens.MoreMoviesScreenRoute
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.baseImageUrl
import com.example.jetfilms.blueGradient
import com.example.jetfilms.encodes.encodeStringWithSpecialCharacter
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun HomeScreen(
    navController: NavController,
    moviesViewModel: MoviesViewModel
) {

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography


    val moviePostersScrollState = rememberLazyListState()
    
    val screenScrollState = rememberSaveable(saver = ScrollState.Saver) {
        ScrollState(0)
    }

    val topRatedMovies = moviesViewModel.topRatedMovies.collectAsStateWithLifecycle()



    val selectedMovieIndex = rememberSaveable{ mutableStateOf(0) }
    val selectedMovie = moviesViewModel.selectedMovie.collectAsStateWithLifecycle()

    LaunchedEffect(selectedMovieIndex.value,topRatedMovies.value) {
        if(topRatedMovies.value.isNotEmpty()) {
            moviesViewModel.selectMovie(topRatedMovies.value[selectedMovieIndex.value].id)
            Log.d("popular movies",topRatedMovies.value.toString())
            Log.d("selected movie",selectedMovie.value.toString())
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
                    model = "$baseImageUrl${selectedMovie.value?.posterUrl}",
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.sdp),
                        modifier = Modifier.padding(bottom = 6.sdp)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.imdb_logo_2016_svg),
                            contentDescription = "IMDb",
                            modifier = Modifier
                                .width(30.sdp)
                                .clip(RoundedCornerShape(3.sdp))
                        )

                        Text(
                            text = selectedMovie.value?.rating.toString(),
                            fontSize = 21f.ssp
                        )
                    }

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
                                    val jsonMovie = Json.encodeToString(
                                        it.copy(
                                            title = encodeStringWithSpecialCharacter(it.title),
                                            overview = encodeStringWithSpecialCharacter(it.overview),
                                            tagline = encodeStringWithSpecialCharacter(it.tagline),
                                            posterUrl = encodeStringWithSpecialCharacter(it.posterUrl.toString()),
                                        )
                                    )
                                    Log.d("json",jsonMovie)
                                    navController.navigate("movie_details/$jsonMovie")
                                }
                            },
                            gradient = blueGradient,
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
                                    gradient = blueGradient,
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

            MoviePager(topRatedMovies.value.take(6), moviePostersScrollState, selectedMovieIndex)

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.sdp),
                modifier = Modifier
                    .padding(top = 17.sdp)
            ) {
                itemsIndexed(topRatedMovies.value.take(6)) { index, movie ->


                    Box(
                        modifier = Modifier
                            .width(21.sdp)
                            .height(5.sdp)
                            .clip(CircleShape)
                            .background(
                                if (index == selectedMovieIndex.value) buttonsColor1 else Color.LightGray.copy(
                                    0.68f
                                )
                            )
                    )
                }
            }

            MoviesCategoryList(
                category = "For You",
                moviesList = simplifiedMoviesDataList,
                navController = navController,
            )

        }
}

@Composable
private fun MoviePager(moviesList: List<SimplifiedMovieDataClass>, lazyListState: LazyListState, selectedMovieIndex:MutableState<Int>) {

    val scope = rememberCoroutineScope()

    LazyRow(
        state = lazyListState,
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(7.sdp),
        modifier = Modifier
            .padding(start = 10.sdp,top = 20.sdp)
    ) {

        items(Int.MAX_VALUE){ index ->
            if(moviesList.isNotEmpty()){
                val movie = simplifiedMoviesDataList[index % moviesList.size]
                Box(
                    modifier = Modifier
                        .width(72.sdp)
                        .height(46.sdp)
                        .clip(RoundedCornerShape(7.sdp))
                ) {

                    AsyncImage(
                        model = "$baseImageUrl${movie.posterUrl}",
                        contentDescription = "movie poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                selectedMovieIndex.value = index % moviesList.size
                                scope.launch {
                                    lazyListState.animateScrollToItem(index)
                                }
                            }
                    )

                    if (selectedMovieIndex.value != index % moviesList.size) {
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

@Composable
private fun MoviesCategoryList(
    category: String,
    moviesList: List<SimplifiedMovieDataClass>,
    navController: NavController
) {
    val typography = MaterialTheme.typography
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 30.sdp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(start = 9.sdp, end = 12.sdp)
                .fillMaxWidth()
        ) {
            Text(
                text = category,
                style = typography.titleLarge,
                fontSize = 25f.ssp,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .width(58.sdp)
                    .height(22.sdp)
                    .clip(RoundedCornerShape(8.sdp))
                    .clickable {
                        navController.navigate(MoreMoviesScreenRoute(category))
                    }
            ){
                Text(
                    text = "See All",
                    style = typography.bodyMedium.copy(
                        brush = blueGradient,
                        fontWeight = FontWeight.Normal
                    ),
                    fontSize = 20.5f.ssp,
                    modifier = Modifier
                        .align(Alignment.Center)

                )
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(9.sdp),
            modifier = Modifier
                .padding(start = 15.sdp,top = 12.sdp, bottom = 62.sdp)
        ) {
            items(moviesList.take(3)) { movie ->
                MovieCard(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.sdp))
                        .width(126.sdp)
                        .height(200.sdp),
                    movie = movie
                )
            }
        }
    }
}