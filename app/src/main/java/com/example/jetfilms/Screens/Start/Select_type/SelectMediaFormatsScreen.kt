package com.example.jetfilms.Screens.Start.Select_type

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetfilms.Components.Buttons.TextButton
import com.example.jetfilms.Components.Buttons.TurnBackButton
import com.example.jetfilms.Screens.Start.SelectMediaGenresScreenRoute
import com.example.jetfilms.Screens.Start.SignUpScreenRoute
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.primex.core.ExperimentalToolkitApi
import com.primex.core.blur.legacyBackgroundBlur
import com.primex.core.noise

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SelectMediaFormatScreen(
    stepsNavController: NavController
) {
    val typography = MaterialTheme.typography

    val selectedFormats = remember{ mutableStateListOf<MediaFormats>() }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((BASE_BUTTON_HEIGHT + 28).sdp)
            ){
                TextButton(
                    onClick = {
                        if(selectedFormats.isNotEmpty()){
                            stepsNavController.navigate(SelectMediaGenresScreenRoute)
                        }
                    },
                    text = if(selectedFormats.isEmpty()) "Select at Least 1" else "Next",
                    textStyle = typography.bodyMedium.copy(
                        color = if(selectedFormats.isEmpty()) Color.Black else Color.White
                    ),
                    corners = RoundedCornerShape(12.sdp),
                    gradient = Brush.horizontalGradient(
                       if(selectedFormats.isEmpty()) listOf(Color.White, Color.White)
                       else listOf(buttonsColor1, buttonsColor2)
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    ) { innerPaddingValues ->
        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize()
        ) {

            TurnBackButton(
                onClick = { stepsNavController.navigate(SignUpScreenRoute)
                          },
                background = Color.LightGray.copy(0.6f),
                iconColor = Color.White,
                modifier = Modifier
                    .padding(start = 16.sdp, top = 34.sdp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.sdp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ){
                Text(
                    text = "Pick What You'd Like to Watch",
                    style = typography.titleLarge.copy(
                        fontSize = typography.titleLarge.fontSize * 1.09,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 22.sdp)
                        .fillMaxWidth(0.82f)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(19.sdp),
                    modifier = Modifier
                        .padding(bottom = 10.sdp)
                ) {
                    MediaFormats.entries.forEach { format ->
                        MediaFormatCard(mediaFormat = format,selectedFormats)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalToolkitApi::class)
@Composable
private fun MediaFormatCard(mediaFormat: MediaFormats,selectedFormats:MutableList<MediaFormats>) {
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
                .legacyBackgroundBlur(
                    radius = 1f,
                    downsample = 0.15f
                )
                .background(Color.White.copy(0.4f))
                .noise(0.12f)
        ){
           Text(
               text = mediaFormat.format,
               style = typography.bodyMedium.copy(fontWeight = FontWeight.Normal, fontSize = typography.bodySmall.fontSize*1.3f),
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