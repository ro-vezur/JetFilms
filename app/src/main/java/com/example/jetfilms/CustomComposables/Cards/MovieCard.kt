package com.example.jetfilms.CustomComposables.Cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.jetfilms.Data_Classes.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.R
import com.example.jetfilms.baseImageUrl
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: SimplifiedMovieDataClass,
) {
    Box(
        modifier = modifier
    ){
        AsyncImage(
            model = "$baseImageUrl${movie.posterUrl}",
            contentDescription = "movie poster",
            contentScale = ContentScale.Crop,
            modifier = modifier
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.sdp),
            modifier = Modifier
                .padding(top = 5.sdp, start = 3.sdp)
                .clip(RoundedCornerShape(3.sdp))
                .background(Color.LightGray.copy(0.18f))
        ){
            Image(
                painter = painterResource(id = R.drawable.imdb_logo_2016_svg),
                contentDescription = "IMDb",
                modifier = Modifier
                    .padding(start = 2.sdp)
                    .width(24.sdp)
                    .clip(RoundedCornerShape(3.sdp))
            )

            Text(
                text = movie.rating.toString(),
                fontSize = 17f.ssp,
                color = Color.White,
                modifier = Modifier
                    .padding(end = 2.sdp)
            )
        }
    }
}