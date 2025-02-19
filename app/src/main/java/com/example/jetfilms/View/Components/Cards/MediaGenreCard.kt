package com.example.jetfilms.View.Components.Cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2

@Composable
fun MediaGenreCard(
    mediaGenre: MediaGenres,
    selected: Boolean,
    onClick: () -> Unit
) {
    val typography = MaterialTheme.typography

    Box(
        modifier = Modifier
            .height(114.sdp)
            .border(
                if (selected) BorderStroke(
                    2.sdp,
                    Brush.horizontalGradient(listOf(buttonsColor1, buttonsColor2))
                )
                else BorderStroke(1.sdp, Color.Transparent),
                RoundedCornerShape(8.sdp)
            )
            .clickable {
                onClick()
            }
    ){
        AsyncImage(
            model = mediaGenre.imageUrl,
            contentDescription = "media genre",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.sdp))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width((mediaGenre.genre.length * 8).sdp)
                .height(23.sdp)
                .background(Color.DarkGray.copy(0.2f), CircleShape)
        ){
            Text(
                text = mediaGenre.genre,
                style = typography.bodyMedium.copy(fontWeight = FontWeight.Normal, fontSize = typography.bodySmall.fontSize*1f),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        if(selected){
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 7.sdp, end = 7.sdp)
                    .size(19.sdp)
                    .clip(CircleShape)
                    .background(Brush.horizontalGradient(listOf(buttonsColor1, buttonsColor2)))

            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "check",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(16.sdp)
                )
            }
        }
    }
}
