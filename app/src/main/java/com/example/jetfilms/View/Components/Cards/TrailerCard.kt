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
import com.example.jetfilms.BASE_YOUTUBE_IMAGES_URL
import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailerObject
import com.example.jetfilms.YOUTUBE_IMAGE_THUMBNAIL_TYPE
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.typography

@Composable
fun TrailerCard(
    modifier: Modifier = Modifier,
    trailerObject: TrailerObject,
    selectedMediaName: String,
) {
    val typography = typography()

    trailerObject.key?.let{
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(5.sdp)
        ) {
            AsyncImage(
                model = "$BASE_YOUTUBE_IMAGES_URL${trailerObject.key}$YOUTUBE_IMAGE_THUMBNAIL_TYPE",
                contentDescription = "episode image",
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
                    text = "$selectedMediaName | ${trailerObject.name}",
                    style = typography.bodyMedium
                )

                Text(
                    text = "",
                    fontSize = typography.bodyMedium.fontSize / 1.1f,
                    color = Color.LightGray.copy(0.84f),
                    modifier = Modifier
                )
            }
        }
    }
}