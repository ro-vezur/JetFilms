package com.example.jetfilms.Screens.ParticipantDetailsPackage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.jetfilms.Additional_functions.navigate.navigateToSelectedMovie
import com.example.jetfilms.CustomComposables.Cards.MovieCard
import com.example.jetfilms.Data_Classes.ParticipantPackage.ParticipantFilmography
import com.example.jetfilms.bottomNavBarHeight
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.states.rememberForeverLazyGridState
import kotlinx.coroutines.launch

@Composable
fun FilmographyScreen(filmography: ParticipantFilmography){
    val gridState = rememberForeverLazyGridState(key = filmography.id.toString())

    Box(
        modifier = Modifier
            .padding(horizontal = 8.sdp)
            .fillMaxSize()
    ){
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(7.sdp),
            verticalArrangement = Arrangement.spacedBy(7.sdp),
            contentPadding = PaddingValues(horizontal = 0.sdp),
         //   userScrollEnabled = false
              state = gridState,

        ) {

            items(filmography.cast.sortedByDescending { it.rating }) { movie ->
                MovieCard(
                    movie = movie,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.sdp))
                        .height(155.sdp)
                        .clickable {

                        }
                )
            }

        }
    }
}