package com.example.jetfilms.CustomComposables.Cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.example.jetfilms.Data_Classes.ParticipantPackage.SimplifiedMovieParticipant
import com.example.jetfilms.baseImageUrl
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

@Composable
fun MovieParticipantCard(
    modifier: Modifier = Modifier,
    movieParticipant: SimplifiedMovieParticipant,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ){
        SubcomposeAsyncImage(
            model = "$baseImageUrl${movieParticipant.image}",
            contentDescription = "movie poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.78f)
                .clip(RoundedCornerShape(8.sdp)),
            loading = { CircularProgressIndicator() }
        )

        Text(
            text = movieParticipant.name,
            fontSize = 17f.ssp,
            modifier = Modifier
                .padding(top = 6.sdp)
        )

        Text(
            text = movieParticipant.activity,
            color = Color.LightGray.copy(0.84f),
            modifier = Modifier
                .padding(top = 2.sdp, start = 1.sdp)
        )
    }
}