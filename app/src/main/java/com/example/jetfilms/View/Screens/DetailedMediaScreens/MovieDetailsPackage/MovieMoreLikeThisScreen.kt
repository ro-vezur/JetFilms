package com.example.jetfilms.View.Screens.DetailedMediaScreens.MovieDetailsPackage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.jetfilms.View.Components.Cards.MovieCard
import com.example.jetfilms.Models.DTOs.MoviePackage.MoviesPageResponse
import com.example.jetfilms.extensions.sdp

@Composable
fun MovieMoreLikeThisScreen(
    similarMovies: MoviesPageResponse,
    selectMovie: (id: Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(9.sdp),
        modifier = Modifier.padding(bottom = 10.sdp)
    ) {
        items(items = similarMovies.results) { movie ->

            MovieCard(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.sdp))
                    .width(126.sdp)
                    .height(200.sdp)
                    .clickable { selectMovie(movie.id) },
                movie = movie
            )

        }
    }
}