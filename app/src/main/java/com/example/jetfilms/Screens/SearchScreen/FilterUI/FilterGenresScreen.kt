package com.example.jetfilms.Screens.SearchScreen.FilterUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Components.Buttons.AcceptFiltersButton
import com.example.jetfilms.Components.Buttons.TextButton
import com.example.jetfilms.Components.Cards.MediaGenreCard
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.states.rememberForeverLazyGridState
import dev.chrisbanes.haze.HazeState

@Composable
fun FilterGenresScreen(
    turnBack: () -> Unit,
    usedGenres: List<MediaGenres>,
    acceptNewGenres: (genres:List<MediaGenres>) -> Unit,
) {
    val grindState = rememberForeverLazyGridState(key = "genre filters")

    val itemsSpacing = 5

    val selectedGenres = remember{ mutableStateListOf<MediaGenres>() }
    val hazeState = remember{ HazeState()}

    LaunchedEffect(null) {
        selectedGenres.clear()
        selectedGenres.addAll(usedGenres)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
        ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(itemsSpacing.sdp),
            verticalArrangement = Arrangement.spacedBy(itemsSpacing.sdp),
            state = grindState,
            contentPadding = PaddingValues(
                horizontal = 4.sdp
            ),
            modifier = Modifier
                .padding(bottom = 6.sdp)
                .fillMaxHeight()
        ) {

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.padding(top = (FILTER_TOP_BAR_HEIGHT + itemsSpacing).sdp))
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

            items(BASE_MEDIA_GENRES) { genre ->
                MediaGenreCard(
                    mediaGenre = genre,
                    selected = selectedGenres.contains(genre),
                    onClick = {
                        if (selectedGenres.contains(genre)) {
                            selectedGenres.remove(genre)
                        } else {
                            selectedGenres.add(genre)
                        }
                    }
                )
            }
            
            item(span = { GridItemSpan(maxLineSpan) }){
                Spacer(modifier = Modifier.padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + BASE_BUTTON_HEIGHT + 6).sdp))
            }
        }

        AcceptFiltersButton(
            isEmpty = selectedGenres.isEmpty(),
            isDataSameAsBefore = selectedGenres.toList().sorted() == usedGenres,
            onClick = {
                turnBack()
                acceptNewGenres(selectedGenres)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 9).sdp)
        )

    }
}