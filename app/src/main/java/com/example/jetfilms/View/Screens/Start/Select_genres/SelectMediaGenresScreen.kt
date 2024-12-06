package com.example.jetfilms.View.Screens.Start.Select_genres

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.View.Screens.Start.SelectMediaFormatScreenRoute
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.View.Components.Cards.MediaGenreCard
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import dev.chrisbanes.haze.HazeState

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SelectMediaGenresScreen(
    stepsNavController: NavController,
) {
    val typography = MaterialTheme.typography

    val selectedGenres = remember{ mutableStateListOf<MediaGenres>() }
    val grindState = rememberLazyGridState()
    val hazeState = remember { HazeState() }

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
                   item{
                       MediaGenreCard(
                           mediaGenre = MediaGenres.ALL,
                           selected = selectedGenres == BASE_MEDIA_GENRES,
                           onClick = {
                               if (selectedGenres.toList().sorted() == BASE_MEDIA_GENRES.sorted()) {
                                   selectedGenres.clear()
                               } else {
                                   BASE_MEDIA_GENRES.forEach { genre ->
                                       if(!selectedGenres.contains(genre)){
                                           selectedGenres.add(genre)
                                       }
                                   }
                               }
                           }
                       )
                   }

                    items(BASE_MEDIA_GENRES){ genre ->
                        MediaGenreCard(
                            mediaGenre = genre,
                            selected = selectedGenres.contains(genre),
                            onClick = {
                                if (selectedGenres.contains(genre)) {
                                    selectedGenres.remove(genre)
                                } else {
                                    selectedGenres.add(genre)
                                }
                            }
                        )
                    }
                }
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
            .height((BASE_BUTTON_HEIGHT + 28).sdp)
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