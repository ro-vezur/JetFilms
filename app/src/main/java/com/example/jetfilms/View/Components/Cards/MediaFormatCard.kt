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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.primex.core.ExperimentalToolkitApi
import com.primex.core.blur.legacyBackgroundBlur
import com.primex.core.noise

@OptIn(ExperimentalToolkitApi::class)
@Composable
fun MediaFormatCard(mediaFormat: MediaFormats, selectedFormats:MutableList<MediaFormats>) {
    val typography = MaterialTheme.typography
    val request = ImageRequest.Builder(LocalContext.current).data(mediaFormat.imageUrl).allowHardware(false).build()

    Box(
        modifier = Modifier
            .width((267).sdp)
            .height(104.sdp)
            .border(
                if (selectedFormats.contains(mediaFormat)) BorderStroke(
                    2f.sdp,
                    Brush.horizontalGradient(listOf(buttonsColor1, buttonsColor2))
                )
                else BorderStroke(1.sdp, Color.Transparent),
                RoundedCornerShape(8.sdp)
            )
            .clickable {
                if (selectedFormats.contains(mediaFormat)) {
                    selectedFormats.remove(mediaFormat)
                } else {
                    selectedFormats.add(mediaFormat)
                }
            }
    ){
        AsyncImage(
            model = request,
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
                .clip(RoundedCornerShape(9.sdp))
                .background(Color.White.copy(0.4f))
        ){
            Text(
                text = mediaFormat.format,
                style = typography.bodySmall.copy(fontWeight = FontWeight.Normal, fontSize = typography.bodySmall.fontSize*1.3f),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        if(selectedFormats.contains(mediaFormat)){
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