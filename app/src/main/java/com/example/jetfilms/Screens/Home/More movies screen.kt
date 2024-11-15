package com.example.jetfilms.Screens.Home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetfilms.Additional_functions.NavigateToSelectedMovie
import com.example.jetfilms.CustomComposables.MovieCard
import com.example.jetfilms.CustomComposables.TurnBackButton
import com.example.jetfilms.Data_Classes.simplifiedMoviesDataList
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.bottomNavBarHeight
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.states.rememberForeverLazyGridState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MoreMoviesScreen(
    navController: NavController,
    category: String,
    moviesViewModel: MoviesViewModel
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    val gridState = rememberForeverLazyGridState(category)

    val scrollOffset by remember { derivedStateOf { gridState.firstVisibleItemScrollOffset } }
    val firstVisibleItemIndex by remember{ derivedStateOf { gridState.firstVisibleItemIndex }}

    val hazeState = remember{HazeState()}

    val scope = rememberCoroutineScope()

    val moreMoviesView = moviesViewModel.moreMoviesView.collectAsStateWithLifecycle()
    val selectedMovie = moviesViewModel.selectedMovie.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = colors.primary,
        topBar = {
            Box(
                modifier = Modifier
                    .height(46.sdp)
                    .hazeChild(state = hazeState)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 6.sdp)
                        .fillMaxWidth()

                ) {
                    TurnBackButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        iconColor = Color.White,
                        size = 29.sdp,
                        modifier = Modifier
                            .padding(start = 12.sdp)
                    )

                    Text(
                        text = category,
                        style = typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.size(29.sdp))
                }
            }
        },
        modifier = Modifier
    ) { innerPadding ->

        val scrollEffect = scrollOffset.sdp / 10
        val showBlur = innerPadding.calculateTopPadding() - scrollEffect >= (0).dp && firstVisibleItemIndex == 0

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
            Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = Color.DarkGray,
                    tint = Color.Black.copy(0.25f),
                    blurRadius = 28.sdp,
                )
        ){
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(
                        top = (
                                if (showBlur)
                                    innerPadding.calculateTopPadding() - scrollOffset.sdp / 10
                                else (0).sdp
                                )
                    )
                    .fillMaxSize(),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(9.sdp),
                verticalArrangement = Arrangement.spacedBy(9.sdp),
                contentPadding = PaddingValues(8.sdp),
                state = gridState,

            ) {
                itemsIndexed(moreMoviesView.value) { index,movie ->
                    movie?.let{
                        MovieCard(
                            movie = movie,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.sdp))
                                .height(205.sdp)
                                .clickable {
                                    scope.launch {
                                        moviesViewModel.selectMovie(movie.id)
                                        delay(250)
                                        selectedMovie.value?.let {
                                            NavigateToSelectedMovie(navController, it)
                                        }
                                    }
                                }
                        )
                    }
                }
                
                item { 
                    Spacer(modifier = Modifier.size((bottomNavBarHeight-6).sdp))
                }
            }
        }
    }
}