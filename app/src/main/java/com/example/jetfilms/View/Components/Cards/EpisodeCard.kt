package com.example.jetfilms.View.Components.Cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.jetfilms.Models.DTOs.SeriesPackage.Episode
import com.example.jetfilms.Helpers.fromMinutesToHours
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.View.Components.OptimizedImage
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.typography

@Composable
fun EpisodeCard(
    modifier: Modifier = Modifier,
    episode: Episode
) {
    val typography = typography()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.sdp)
    ) {
        OptimizedImage(
            url = BASE_IMAGE_API_URL + episode.image.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .width(150.sdp)
                .clip(RoundedCornerShape(8.sdp))
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 4.sdp),
            verticalArrangement = Arrangement.spacedBy(4.sdp)
        ) {
            Text(
                text = "${episode.episode_number}. ${episode.name}",
                style = typography.bodyMedium
            )

            Text(
                text = fromMinutesToHours(episode.runtime),
                fontSize = typography.bodyMedium.fontSize / 1.1f,
                color = Color.LightGray.copy(0.84f),
                modifier = Modifier
            )
        }
    }
}