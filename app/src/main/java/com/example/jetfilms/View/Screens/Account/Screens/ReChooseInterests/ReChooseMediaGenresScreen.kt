package com.example.jetfilms.View.Screens.Account.Screens.ReChooseInterests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.View.Components.Buttons.AcceptMultipleSelectionButton
import com.example.jetfilms.View.Components.Cards.MediaGenreCard
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun ReChooseMediaGenresScreen(
    usedMediaGenres: List<MediaGenres>,
    turnBack: () -> Unit,
    acceptNewMediaGenres: (mediaGenres: List<MediaGenres>) -> Unit,
) {
    val hazeState = remember { HazeState() }

    val selectedGenres = remember{ mutableStateListOf<MediaGenres>() }
    val grindState = rememberLazyGridState()

    LaunchedEffect(null) {
        selectedGenres.addAll(usedMediaGenres)
    }

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
                modifier = Modifier
                    .hazeChild(hazeState),
                headerText = "Re-Choose Genres",
                turnBack = turnBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = HAZE_STATE_BLUR.sdp,
                )
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(5.sdp),
                verticalArrangement = Arrangement.spacedBy(5.sdp),
                state = grindState,
                contentPadding = PaddingValues(
                    horizontal = 4.sdp
                ),
                modifier = Modifier
                    .padding(bottom = 6.sdp)
                    .fillMaxHeight()
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(FILTER_TOP_BAR_HEIGHT.sdp))
                }
                
                item{
                    MediaGenreCard(
                        mediaGenre = MediaGenres.ALL,
                        selected = selectedGenres == BASE_MEDIA_GENRES,
                        onClick = {
                            if (selectedGenres.toList().sorted() == BASE_MEDIA_GENRES.sorted()) {
                                selectedGenres.clear()
                            } else {
                                BASE_MEDIA_GENRES.forEach { genre ->
                                    if(!selectedGenres.contains(genre)){
                                        selectedGenres.add(genre)
                                    }
                                }
                            }
                        }
                    )
                }

                items(BASE_MEDIA_GENRES){ genre ->
                    val selected = selectedGenres.contains(genre)
                    MediaGenreCard(
                        mediaGenre = genre,
                        selected = selected,
                        onClick = {
                            if (selected) {
                                selectedGenres.remove(genre)
                            } else {
                                selectedGenres.add(genre)
                            }
                        }
                    )
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(BOTTOM_NAVIGATION_BAR_HEIGHT.sdp))
                }
            }

            AcceptMultipleSelectionButton(
                isEmpty = selectedGenres.isEmpty(),
                isDataSameAsBefore = selectedGenres.toList().sorted() == usedMediaGenres.sorted(),
                onAcceptText = "Accept New Genres",
                onClick = { acceptNewMediaGenres(selectedGenres) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 20).sdp)
            )
        }
    }
}