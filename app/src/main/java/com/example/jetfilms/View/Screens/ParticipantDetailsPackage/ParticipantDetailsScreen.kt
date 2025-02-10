package com.example.jetfilms.View.Screens.ParticipantDetailsPackage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.View.Components.Cards.NeonCard
import com.example.jetfilms.View.Components.Gradient.animatedGradient
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.View.Components.Cards.MovieCard
import com.example.jetfilms.Models.Enums.AnimatedGradientTypes
import com.example.jetfilms.Helpers.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParicipantResponses.DetailedParticipantResponse
import com.example.jetfilms.View.Components.CustomTabRow
import com.example.jetfilms.ViewModels.DetailedMediaViewModels.DetailedParticipantViewModel
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ParticipantDetailsScreen(
    navController: NavController,
    participantResponse: DetailedParticipantResponse,
    selectMovie: (id: Int) -> Unit
) {
    val detailedParticipantViewModel = hiltViewModel<DetailedParticipantViewModel,DetailedParticipantViewModel.DetailedParticipantViewModelFactory> { factory ->
        factory.create(participantResponse.id)
    }

    val participantFilmography by detailedParticipantViewModel.selectedParticipantFilmography.collectAsStateWithLifecycle()
    val participantImages by detailedParticipantViewModel.selectedParticipantImages.collectAsStateWithLifecycle()

    val colors = MaterialTheme.colorScheme

    val tabs = listOf(
        "Filmography",
        "Biography",
    )

    val selectedTabsIndex = remember { mutableStateOf(0) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val filmographyColumns = 3
    val sortedMoviesList by remember(participantFilmography) {
        mutableStateOf(
            participantFilmography.cast
                .sortedByDescending { movie -> movie.popularity }
                .chunked(filmographyColumns)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(7.sdp),
            contentPadding = PaddingValues(horizontal = 0.sdp),
            userScrollEnabled = true,
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primary)
        ) {
            item{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(315.sdp)
                ) {
                    AsyncImage(
                        model = "$BASE_IMAGE_API_URL${
                            decodeStringWithSpecialCharacter(
                                participantResponse.image.toString()
                            )
                        }",
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        colors.primary.copy(0.58f),
                                        colors.primary.copy(1f),
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 15.sdp)
                    ) {
                        Text(
                            text = decodeStringWithSpecialCharacter(participantResponse.name),
                            style = typography().titleLarge,
                        )

                    }
                }
            }

            item{
                CustomTabRow(
                    tabs = tabs,
                    selectedTabIndex = selectedTabsIndex
                )
            }

            if(selectedTabsIndex.value == 0){

                items(sortedMoviesList){ chunk ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        chunk.forEach { movie ->
                            MovieCard(
                                movie = movie,
                                modifier = Modifier
                                    .padding(horizontal = 2.sdp)
                                    .width(screenWidth / filmographyColumns)
                                    .height(155.sdp)
                                    .clip(RoundedCornerShape(8.sdp))
                                    .clickable { selectMovie(movie.id) }
                            )
                        }
                    }
                }

            }

            item {
                AnimatedVisibility(visible = selectedTabsIndex.value == 1) {
                    BiographyScreen(
                        participantDisplay = DetailedParticipantDisplay(
                            participantResponse = participantResponse,
                            filmography = participantFilmography,
                            images = participantImages,
                        )
                    )
                }
            }
        }



        TurnBackButton(
            onClick = {
                navController.navigateUp()
            },
            background = Color.LightGray.copy(0.6f),
            iconColor = Color.White,
            modifier = Modifier
                .padding(start = 8.sdp, top = 34.sdp)
        )
    }
}