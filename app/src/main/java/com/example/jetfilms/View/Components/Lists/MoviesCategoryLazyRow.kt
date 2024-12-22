package com.example.jetfilms.View.Components.Lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.jetfilms.View.Components.Cards.MovieCard
import com.example.jetfilms.Models.DTOs.MoviePackage.SimplifiedMovieResponse
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.typography

@Composable
fun MoviesCategoryList(
    category: String,
    selectMovie: (id: Int) -> Unit,
    moviesList: List<SimplifiedMovieResponse>,
    onSeeAllClick: () -> Unit,
    topPadding: Dp = 0.sdp,
    bottomPadding: Dp = 0.sdp,
    showSeeAllButton: Boolean = true,
    imageModifier: Modifier = Modifier
) {

    if(moviesList.isNotEmpty()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(start = 10.sdp, end = 12.sdp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = category,
                    style = typography().headlineLarge,
                    color = Color.White,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.weight(1f))

                if (showSeeAllButton) {
                    Box(
                        modifier = Modifier
                            .width(58.sdp)
                            .height(22.sdp)
                            .clip(RoundedCornerShape(8.sdp))
                            .clickable {
                                onSeeAllClick()
                            }
                    ) {
                        Text(
                            text = "See All",
                            style = typography().bodyMedium.copy(
                                brush = blueHorizontalGradient,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)

                        )
                    }
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(9.sdp),
                modifier = Modifier
                    .padding(start = 15.sdp, top = 14.sdp, bottom = bottomPadding)
            ) {
                items(items = moviesList) { movie ->

                    MovieCard(
                        modifier = imageModifier
                            .clickable { selectMovie(movie.id) },
                        movie = movie
                    )

                }
            }
        }
    }
}