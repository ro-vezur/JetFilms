package com.example.jetfilms.View.Screens.Home

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetfilms.View.Components.Cards.MovieCard
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.View.states.rememberForeverLazyGridState
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MoreMoviesScreen(
    turnBack: () -> Unit,
    selectMovie: (id: Int) -> Unit,
    category: String,
    moviesViewModel: MoviesViewModel,
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    val moreMoviesView = moviesViewModel.moreMoviesView.collectAsLazyPagingItems()
    val gridState = rememberForeverLazyGridState(category)
    val hazeState = remember{HazeState()}
    val scope = rememberCoroutineScope()

    val topBarHeight = 52.sdp

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            Box(
                modifier = Modifier
                    .height(topBarHeight)
                    .fillMaxWidth()
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
                        onClick = { turnBack() },
                        iconColor = Color.White,
                        size = 29.sdp,
                        modifier = Modifier
                            .padding(start = 12.sdp)
                    )

                    Text(
                        text = category,
                        style = typography.headlineLarge,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.size(29.sdp))
                }
            }
        },
        modifier = Modifier
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
              //  .padding(innerPadding)
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = HAZE_STATE_BLUR.sdp,
                    noiseFactor = 0f
                )
        ){
           when {
               moreMoviesView.loadState.refresh is LoadState.Loading -> {
                   CircularProgressIndicator()
               }
              else ->  LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(9.sdp),
                    verticalArrangement = Arrangement.spacedBy(9.sdp),
                    contentPadding = PaddingValues(8.sdp),
                    state = gridState,

                    ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Spacer(
                            modifier = Modifier.height(
                                topBarHeight
                            )
                        )
                    }

                    items(moreMoviesView.itemCount) { index ->
                        val movie = moreMoviesView[index]
                        movie?.let {
                            MovieCard(
                                movie = movie,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.sdp))
                                    .height(205.sdp)
                                    .clickable {
                                        selectMovie(movie.id)
                                    }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.size((BOTTOM_NAVIGATION_BAR_HEIGHT - 6).sdp))
                    }
                }
            }
        }
    }
}