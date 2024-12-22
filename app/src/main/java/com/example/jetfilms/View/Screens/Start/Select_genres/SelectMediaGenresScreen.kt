package com.example.jetfilms.View.Screens.Start.Select_genres

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.View.Components.Cards.MediaGenreCard
import com.example.jetfilms.View.Components.Gradient.animatedGradient
import com.example.jetfilms.blueGradientColors
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.whiteColor
import com.example.jetfilms.whiteGradientColors

@Composable
fun SelectMediaGenresScreen(
    stepsNavController: NavController,
    user: User,
    setUser: (newUser: User) -> Unit,
    signUp: (userToAdd: User) -> Unit,
) {
    val typography = MaterialTheme.typography

    val selectedGenres = remember{ mutableStateListOf<MediaGenres>() }
    val grindState = rememberLazyGridState()

    LaunchedEffect(null) {
        selectedGenres.addAll(user.recommendedMediaGenres)
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            AcceptMediaGenresButton(
                isNotEmpty = selectedGenres.isNotEmpty(),
                onClick = {
                    if(selectedGenres.isNotEmpty()) {
                        signUp(user)
                    }
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
                onClick = { stepsNavController.navigateUp() },
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
                                   setUser(
                                       user.copy(recommendedMediaGenres = selectedGenres)
                                   )
                               } else {
                                   BASE_MEDIA_GENRES.forEach { genre ->
                                       if(!selectedGenres.contains(genre)){
                                           selectedGenres.add(genre)
                                           setUser(
                                               user.copy(recommendedMediaGenres = selectedGenres)
                                           )
                                       }
                                   }
                               }
                           }
                       )
                   }

                    items(BASE_MEDIA_GENRES){ genre ->
                        val selected = selectedGenres.contains(genre)
                        MediaGenreCard(
                            mediaGenre = genre,
                            selected = selected,
                            onClick = {
                                if (selected) {
                                    selectedGenres.remove(genre)
                                    setUser(
                                        user.copy(recommendedMediaGenres = selectedGenres)
                                    )
                                } else {
                                    selectedGenres.add(genre)
                                    setUser(
                                        user.copy(recommendedMediaGenres = selectedGenres)
                                    )
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
private fun AcceptMediaGenresButton(isNotEmpty:Boolean, onClick:() -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((BASE_BUTTON_HEIGHT + 28).sdp)
    ){
        TextButton(
            onClick = onClick,
            text = if(isNotEmpty) "Done" else "Select at Least 1",
            textColor = if(isNotEmpty) whiteColor else Color.Black,
            corners = RoundedCornerShape(12.sdp),
            gradient = animatedGradient(
                colors = if(isNotEmpty) blueGradientColors
                else whiteGradientColors
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}