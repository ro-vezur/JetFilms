package com.example.jetfilms.Screens.MovieDetailsPackage

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetfilms.View.Components.Gradient.GradientIcon
import com.example.jetfilms.View.Components.Cards.NeonCard
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.View.Components.Cards.EpisodeCard
import com.example.jetfilms.View.Components.Gradient.animatedGradient
import com.example.jetfilms.Models.DTOs.SeriesPackage.SerialSeasonResponse
import com.example.jetfilms.Helpers.DateFormats.DateFormats
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.View.Components.Cards.PropertyCard
import com.example.jetfilms.View.Components.DetailedMediaComponents.DisplayRating
import com.example.jetfilms.View.Components.CustomTabRow
import com.example.jetfilms.View.Components.TabsContent.SeriesAboutTab
import com.example.jetfilms.Models.Enums.AnimatedGradientTypes
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.Helpers.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.Helpers.navigate.navigateToSelectedParticipant
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParicipantResponses.SimplifiedParticipantResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSeriesResponse
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesDisplay
import com.example.jetfilms.View.Components.DetailedMediaComponents.MediaTitle
import com.example.jetfilms.View.Screens.DetailedMediaScreens.TrailerScreen
import com.example.jetfilms.ViewModels.DetailedMediaViewModels.DetailedSeriesViewModel
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.mediaAboutTabs
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.typography
import kotlinx.coroutines.launch

@Composable
fun SerialDetailsScreen(
    navController: NavController,
    seriesResponse: DetailedSeriesResponse,
    selectSerial: (id: Int) -> Unit,
    addToFavorite: (favoriteMedia: FavoriteMedia) -> Unit,
    isFavoriteUnit: (favoriteMedia: FavoriteMedia) -> Boolean,
) {
    val detailedSeriesViewModel = hiltViewModel<DetailedSeriesViewModel,DetailedSeriesViewModel.DetailedSeriesViewModelFactory> { factory ->
        factory.create(seriesResponse.id)
    }

    val seriesCast = detailedSeriesViewModel.seriesCast.collectAsStateWithLifecycle()
    val seriesImages = detailedSeriesViewModel.seriesImages.collectAsStateWithLifecycle()
    val similarSeries = detailedSeriesViewModel.similarSeries.collectAsStateWithLifecycle()
    val seriesTrailers = detailedSeriesViewModel.seriesTrailers.collectAsStateWithLifecycle()

    val seasonTabs = seriesResponse.seasons

    var imageHeight by rememberSaveable{ mutableStateOf(290) }
    val scope = rememberCoroutineScope()

    var selectedSeasonTabIndex by rememberSaveable { mutableStateOf(0) }
    var selectedSeason by remember { mutableStateOf<SerialSeasonResponse?>(null) }

    var selectedTrailerKey by rememberSaveable { mutableStateOf<String?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    val selectedSeriesAboutTabIndex = rememberSaveable { mutableStateOf(0) }

    val selectParticipant = { participant: SimplifiedParticipantResponse ->
        scope.launch {
            navigateToSelectedParticipant(
                navController = navController,
                selectedParticipantResponse = detailedSeriesViewModel.getParticipant(participant.id)
            )
        }
    }

    LaunchedEffect(Unit) {
        imageHeight = 335
        isFavorite = isFavoriteUnit(FavoriteMedia.fromDetailedSeriesResponse(seriesResponse))
    }

    LaunchedEffect(selectedSeasonTabIndex) {
        selectedSeason =
           try {
               detailedSeriesViewModel.getSerialSeason(seriesResponse.id,selectedSeasonTabIndex)
           } catch (e:Exception){
               detailedSeriesViewModel.getSerialSeason(seriesResponse.id,selectedSeasonTabIndex + 1)
           }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.primary)
        ) {
            item{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(animateIntAsState(targetValue = imageHeight).value.sdp)
                ) {
                    AsyncImage(
                        model = "$BASE_IMAGE_API_URL${
                            decodeStringWithSpecialCharacter(
                                seriesResponse.poster.toString()
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
                                        colorScheme.primary.copy(0.64f),
                                        colorScheme.primary.copy(1f),
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 15.sdp)
                    ) {
                        DisplayRating(seriesResponse.rating)

                        MediaTitle(decodeStringWithSpecialCharacter(seriesResponse.name.toString()))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.sdp),
                            modifier = Modifier.padding(top = 14.sdp, start = 3.sdp, bottom = 6.sdp)
                        ) {
                            if (seriesResponse.releaseDate.isNotBlank()) {
                                PropertyCard(DateFormats.getYear(seriesResponse.releaseDate).toString())
                            }

                            if (seriesResponse.genres.isNotEmpty()) {
                                PropertyCard(text = seriesResponse.genres.first().name,)
                            }

                            if (seriesResponse.originCountries.isNotEmpty()) {
                                PropertyCard(seriesResponse.originCountries.first())
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.sdp),
                            modifier = Modifier.padding(top = 18.sdp, start = 3.sdp, bottom = 6.sdp)
                        ) {
                            TextButton(
                                onClick = {},
                                gradient = blueHorizontalGradient,
                                width = 138.sdp,
                                height = 36.sdp,
                                corners = RoundedCornerShape(18.sdp),
                                textAlign = Alignment.Center,
                                text = "Watch Now",
                                modifier = Modifier
                            )

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(32.sdp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isFavorite) colorScheme.secondary.copy(.85f)
                                        else colorScheme.secondary.copy(.92f)
                                    )
                                    .clickable {
                                        isFavorite = !isFavorite
                                        addToFavorite(
                                            FavoriteMedia.fromDetailedSeriesResponse(
                                                seriesResponse
                                            )
                                        )
                                    }
                            ) {
                                if (isFavorite) {
                                    GradientIcon(
                                        icon = Icons.Filled.Bookmarks,
                                        contentDescription = "favorite",
                                        gradient = blueHorizontalGradient,
                                        modifier = Modifier
                                            .size(20.sdp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Outlined.Bookmarks,
                                        contentDescription = "unfavorable",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(20.sdp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item{
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(12.sdp),
                    modifier = Modifier
                        .padding(start = 14.sdp, end = 14.sdp, top = 16.sdp)
                ) {
                    Text(
                        text = "${seriesResponse.seasons.size} Season" + if (seriesResponse.seasons.size == 1) "" else "s",
                        fontSize = 16.ssp,
                        fontWeight = FontWeight.W500
                    )

                    Text(
                        text = decodeStringWithSpecialCharacter(seriesResponse.overview),
                        fontSize = 13.ssp,
                        color = Color.LightGray.copy(0.9f),
                        fontWeight = FontWeight.W400,
                        modifier = Modifier
                            .padding(start = 2.sdp)
                    )
                }
            }

            item{
                ScrollableTabRow(
                    modifier = Modifier
                        .padding(top = 8.sdp, start = 6.sdp, end = 6.sdp)
                        .fillMaxWidth(),
                    edgePadding = 0.sdp,
                    selectedTabIndex = selectedSeasonTabIndex, divider = {},
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        if (selectedSeasonTabIndex < tabPositions.size) {
                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(2.sdp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray.copy(0.42f))
                                )

                                NeonCard(
                                    glowingColor = buttonsColor1,
                                    containerColor = buttonsColor2,
                                    cornersRadius = Int.MAX_VALUE.sdp,
                                    glowingRadius = 7.sdp,
                                    modifier = Modifier
                                        .tabIndicatorOffset(tabPositions[selectedSeasonTabIndex])
                                        .height(2.sdp)
                                )
                            }
                        }
                    }
                ) {
                    seasonTabs.forEachIndexed { index, season ->
                        if (season.episodeCount != 0) {
                            val selected = selectedSeasonTabIndex == index

                            var selectedColor1 by remember { mutableStateOf(buttonsColor1) }
                            var selectedColor2 by remember { mutableStateOf(buttonsColor2) }

                            if (selected) {
                                selectedColor1 = buttonsColor1
                                selectedColor2 = buttonsColor2
                            } else {
                                selectedColor1 = Color.LightGray.copy(0.42f)
                                selectedColor2 = Color.LightGray.copy(0.42f)
                            }

                            Tab(
                                text = {
                                    Text(
                                        text = "Season ${
                                            if (seriesResponse.seasons.first().seasonNumber == 0) season.seasonNumber + 1
                                            else season.seasonNumber
                                        }",
                                        style = typography().bodySmall.copy(
                                            brush = animatedGradient(
                                                colors = listOf(selectedColor1, selectedColor2),
                                                type = AnimatedGradientTypes.VERTICAL
                                            ),
                                        )
                                    )
                                },
                                selectedContentColor = buttonsColor1,
                                unselectedContentColor = primaryColor,
                                selected = selected,
                                onClick = { selectedSeasonTabIndex = index },
                                modifier = Modifier
                            )
                        }
                    }
                }
            }

            selectedSeason?.let {
                items(it.episodes){ episode ->
                    EpisodeCard(
                        modifier = Modifier
                            .padding(horizontal = 6.sdp, vertical = 6.sdp)
                            .fillMaxWidth()
                            .height(105.sdp),
                        episode = episode
                    )
                }
            }

            item{
                CustomTabRow(
                    tabs = mediaAboutTabs,
                    selectedTabIndex = selectedSeriesAboutTabIndex,
                    modifier = Modifier
                        .padding(top = 12.sdp, start = 6.sdp, end = 6.sdp)
                )
            }

            item{
                if(seriesCast.value != null && seriesImages.value != null && similarSeries.value != null && seriesTrailers.value != null){
                    SeriesAboutTab(
                        selectedTabIndex = selectedSeriesAboutTabIndex.value,
                        navigateToSelectedParticipant = { selectParticipant(it) },
                        selectSeries = selectSerial,
                        seriesDisplay = SeriesDisplay(
                            response = seriesResponse,
                            seriesCast = seriesCast.value!!,
                            seriesImages = seriesImages.value!!,
                            similarSeries = similarSeries.value!!,
                            seriesTrailers = seriesTrailers.value!!
                        ),
                        selectTrailer = { trailer ->
                            selectedTrailerKey = trailer.key
                        },
                        modifier = Modifier
                            .padding(top = 18.sdp, bottom = 0.sdp),
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

        if(selectedTrailerKey != null) {
            TrailerScreen(
                trailerKey = selectedTrailerKey!!,
                onDismiss = {
                    selectedTrailerKey = null
                }
            )
        }
    }
}