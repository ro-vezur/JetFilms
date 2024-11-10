package com.example.jetfilms.Screens.Start.Select_genres

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetfilms.CustomComposables.TextButton
import com.example.jetfilms.CustomComposables.TurnBackButton
import com.example.jetfilms.R
import com.example.jetfilms.Screens.Start.SelectMediaFormatScreenRoute
import com.example.jetfilms.Screens.Start.SignUpScreenRoute
import com.example.jetfilms.baseButtonHeight
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.primex.core.VerticalGrid
import com.primex.core.blur.legacyBackgroundBlur
import com.primex.core.noise

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SelectMediaGenresScreen(
    stepsNavController: NavController,
) {
    val typography = MaterialTheme.typography

    val selectedGenres = remember{ mutableStateListOf<MediaGenres>() }

    val grindState = rememberLazyGridState()

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            BottomBar(
                selectedItems = selectedGenres.isEmpty(),
                onClick = {

                }
            )
        }
    ) { innerPaddingValues ->
        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize()
        ) {

            TurnBackButton(
                onClick = { stepsNavController.navigate(SelectMediaFormatScreenRoute)
                },
                background =  Color.LightGray.copy(0.6f),
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
                        fontSize = typography.titleLarge.fontSize * 1.1f,
                        fontWeight = FontWeight.SemiBold,
                      //  fontStyle = FontStyle(R.font.roboto_medium)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 22.sdp)
                        .fillMaxWidth(0.82f)
                )

               LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(5.sdp),
                    verticalArrangement = Arrangement.spacedBy(5.sdp),
                   state = grindState,
                    contentPadding = PaddingValues(
                        horizontal = 4.sdp
                    ),
                    modifier = Modifier
                        .padding(bottom = 6.sdp)
                        .fillMaxHeight(0.8f)
                ) {

                    items(MediaGenres.entries){genre ->
                        MediaGenreCard(mediaGenre = genre,selectedGenres)
                    }
                }
            }
        }
    }
}

@Composable
private fun MediaGenreCard(mediaGenre: MediaGenres, selectedGenres:MutableList<MediaGenres>) {
    val typography = MaterialTheme.typography
    val request = ImageRequest.Builder(LocalContext.current).data(mediaGenre.imageUrl).allowHardware(false).build()

    Box(
        modifier = Modifier

            .height(114.sdp)
            .border(
                if (selectedGenres.contains(mediaGenre)) BorderStroke(
                    2f.sdp,
                    Brush.horizontalGradient(listOf(buttonsColor1, buttonsColor2))
                )
                else BorderStroke(1.sdp, Color.Transparent),
                RoundedCornerShape(8.sdp)
            )
            .clickable {
                if (selectedGenres.contains(mediaGenre)) {
                    selectedGenres.remove(mediaGenre)
                } else {
                    selectedGenres.add(mediaGenre)
                }
            }
    ){
        AsyncImage(
            model = request,
            contentDescription = "genre image",
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
                .clip(RoundedCornerShape(9.sdp))

                .background(Color.Gray.copy(0.4f))
             //   .noise(0.12f)
        ){
            Text(
                text = mediaGenre.genre,
                style = typography.bodyMedium.copy(fontWeight = FontWeight.Normal, fontSize = typography.bodySmall.fontSize*1f),
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        if(selectedGenres.contains(mediaGenre)){
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

@Composable
private fun BottomBar(selectedItems:Boolean,onClick:() -> Unit) {
    val typography = MaterialTheme.typography

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((baseButtonHeight + 28).sdp)
    ){
        TextButton(
            onClick = onClick,
            text = if(selectedItems) "Select at Least 1" else "Done",
            textStyle = typography.bodyMedium.copy(
                color = if(selectedItems) Color.Black else Color.White
            ),
            corners = RoundedCornerShape(12.sdp),
            gradient = Brush.horizontalGradient(
                if(selectedItems) listOf(Color.White, Color.White)
                else listOf(buttonsColor1, buttonsColor2)
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}