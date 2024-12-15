package com.example.jetfilms.View.Screens.ParticipantDetailsPackage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.jetfilms.View.Components.Cards.MovieCard
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.View.states.rememberForeverLazyGridState
@Composable
fun FilmographyScreen(
    participantDisplay: DetailedParticipantDisplay,
    selectMovie:(id: Int) -> Unit,
){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val filmography = participantDisplay.filmography
    val gridState = rememberForeverLazyGridState(key = participantDisplay.participantResponse.id.toString())

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 8.sdp)
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(7.sdp),
        verticalArrangement = Arrangement.spacedBy(7.sdp),
        contentPadding = PaddingValues(horizontal = 0.sdp),
        userScrollEnabled = true,
        state = gridState,

        ) {
        item(span = {GridItemSpan(maxLineSpan)}) {
            Spacer(modifier = Modifier.height(7.sdp))
        }

        items(filmography.cast.sortedByDescending { it.rating }) { movie ->
            MovieCard(
                movie = movie,
                modifier = Modifier
                    .height(155.sdp)
                    .width(70.sdp)
                    .clip(RoundedCornerShape(8.sdp))
                    .clickable {selectMovie(movie.id)}
            )
        }

    }
}