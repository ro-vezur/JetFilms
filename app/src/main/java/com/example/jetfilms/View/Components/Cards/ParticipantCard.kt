package com.example.jetfilms.View.Components.Cards

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
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParicipantResponses.SimplifiedParticipantResponse
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

@Composable
fun MovieParticipantCard(
    modifier: Modifier = Modifier,
    movieParticipant: SimplifiedParticipantResponse,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ){
        AsyncImage(
            model = "$BASE_IMAGE_API_URL${movieParticipant.image}",
            contentDescription = "participant image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.72f)
                .clip(RoundedCornerShape(8.sdp)),
        )

        Text(
            text = movieParticipant.name,
            fontSize = 14.ssp,
            modifier = Modifier
                .padding(top = 6.sdp)
        )

        Text(
            text = movieParticipant.activity,
            color = Color.LightGray.copy(0.84f),
            fontSize = 12.ssp,
            modifier = Modifier
                .padding(top = 2.sdp, start = 1.sdp)
        )
    }
}