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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.primex.core.ExperimentalToolkitApi
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalToolkitApi::class)
@Composable
fun MediaFormatCard(mediaFormat: MediaCategories, selected: Boolean, onClick: () -> Unit) {
    val typography = MaterialTheme.typography


    Box(
        modifier = Modifier
            .width((267).sdp)
            .height(104.sdp)
            .border(
                if (selected) BorderStroke(
                    2f.sdp,
                    Brush.horizontalGradient(listOf(buttonsColor1, buttonsColor2))
                )
                else BorderStroke(1.sdp, Color.Transparent),
                RoundedCornerShape(8.sdp)
            )
            .clickable { onClick() }
    ){
        AsyncImage(
            model = mediaFormat.imageUrl,
            contentDescription = "image poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.sdp))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width((mediaFormat.format.length * 11).sdp)
                .height(24.sdp)
                .background(Color.Gray.copy(0.2f), CircleShape)
        ){
            Text(
                text = mediaFormat.format,
                style = typography.bodySmall.copy(fontWeight = FontWeight.Normal, fontSize = typography.bodySmall.fontSize*1.3f),
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
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(16.sdp)
                )
            }
        }
    }
}