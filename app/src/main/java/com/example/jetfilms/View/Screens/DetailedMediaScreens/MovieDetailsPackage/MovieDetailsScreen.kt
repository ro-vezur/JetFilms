package com.example.jetfilms.View.Screens.DetailedMediaScreens.MovieDetailsPackage

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.jetfilms.Helpers.fromMinutesToHours
import com.example.jetfilms.View.Components.Gradient.GradientIcon
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.Models.DTOs.MoviePackage.MovieDisplay
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.SimplifiedParticipantResponse
import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.Helpers.DTOsConverters.DetailedMovieDataToFavoriteMedia
import com.example.jetfilms.View.Components.Cards.PropertyCard
import com.example.jetfilms.View.Components.DetailedMediaComponents.DisplayRating
import com.example.jetfilms.View.Components.MediaInfoTabRow
import com.example.jetfilms.View.Components.TabsContent.MovieAboutTab
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.Helpers.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.View.Screens.DetailedMediaScreens.TrailerScreen
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.infoTabs

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movieDisplay: MovieDisplay,
    selectMovie: (id: Int) -> Unit,
    selectParticipant: (participant: SimplifiedParticipantResponse) -> Unit,
    addToFavorite: (favoriteMedia: FavoriteMedia) -> Unit,
    isFavoriteUnit: (favoriteMedia: FavoriteMedia) -> Boolean,
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val movieResponse = movieDisplay.response

    val scrollState = rememberScrollState()
    val tabPagerState = rememberPagerState(pageCount = { infoTabs.size})

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current

    val activity = context as Activity

    var imageHeight by rememberSaveable{ mutableStateOf(290) }
    var selectedTrailerKey by rememberSaveable { mutableStateOf<String?>(null) }

    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        isFavorite = isFavoriteUnit(DetailedMovieDataToFavoriteMedia(movieResponse))
    }

    LaunchedEffect(currentBackStackEntry) {
        imageHeight = if(currentBackStackEntry?.destination?.route == "movie_details/{movie}"){
            335
        } else{
            290
        }
    }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.primary)
                    .verticalScroll(scrollState)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(animateIntAsState(targetValue = imageHeight).value.sdp)
                ) {
                    AsyncImage(
                        model = "$BASE_IMAGE_API_URL${decodeStringWithSpecialCharacter(movieResponse.posterUrl.toString())}",
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
                                        colors.primary.copy(0.64f),
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
                        DisplayRating(movieResponse.rating)

                        Text(
                            text = decodeStringWithSpecialCharacter(movieResponse.title),
                            style = typography.titleLarge,
                            fontSize = 26f.ssp,
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.sdp),
                            modifier = Modifier.padding(top = 14.sdp, start = 3.sdp, bottom = 6.sdp)
                        ) {
                            if(movieResponse.releaseDate.isNotBlank()) {
                                PropertyCard(
                                    text = DateFormats.getYear(movieResponse.releaseDate).toString(),
                                    lengthMultiplayer = 13
                                )
                            }

                            if(movieResponse.genres.isNotEmpty()){
                                PropertyCard(
                                    text = movieResponse.genres.first().name,
                                    lengthMultiplayer = 8
                                )
                            }

                            if(movieResponse.originCountries.isNotEmpty()){
                                PropertyCard(
                                    text = movieResponse.originCountries.first(),
                                    lengthMultiplayer = 21
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.sdp),
                            modifier = Modifier.padding(top = 18.sdp, start = 3.sdp, bottom = 6.sdp)
                        ) {
                            TextButton(
                                onClick = {

                                },
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
                                    .padding(start = 6.sdp)
                                    .size(32.sdp)
                                    .clip(CircleShape)
                                    .background(
                                        if (false) colors.secondary.copy(.85f)
                                        else colors.secondary.copy(.92f)
                                    )
                            ) {
                                if (false) {
                                    GradientIcon(
                                        icon = Icons.Filled.Download,
                                        contentDescription = "download",
                                        gradient = blueHorizontalGradient,
                                        modifier = Modifier
                                            .size(20.sdp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Outlined.Download,
                                        contentDescription = "download",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(24.sdp)
                                    )
                                }
                            }

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(32.sdp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isFavorite) colors.secondary.copy(.85f)
                                        else colors.secondary.copy(.92f)
                                    )
                                    .clickable {
                                        isFavorite = !isFavorite
                                        addToFavorite(
                                            DetailedMovieDataToFavoriteMedia(
                                                movieResponse
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

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(12.sdp),
                    modifier = Modifier
                        .padding(start = 14.sdp,end = 14.sdp,top = 16.sdp)
                       // .fillMaxWidth()
                ) {
                    Text(
                        text = fromMinutesToHours(movieResponse.runtime),
                        fontSize = 15.ssp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier
                    )

                    Text(
                        text = decodeStringWithSpecialCharacter(movieResponse.overview),
                        fontSize = 13.ssp,
                        color = Color.LightGray.copy(0.9f),
                        fontWeight = FontWeight.W400,
                        modifier = Modifier
                            .padding(start = 2.sdp)
                    )
                }

                MediaInfoTabRow(
                    tabs = infoTabs,
                    pagerState = tabPagerState,
                    modifier = Modifier
                        .padding(top = 12.sdp, start = 6.sdp,end = 6.sdp)
                )

                MovieAboutTab(
                    pagerState = tabPagerState,
                    movieDisplay = movieDisplay,
                    selectMovie = selectMovie,
                    navigateToSelectedParticipant = selectParticipant,
                    selectTrailer = { trailer ->
                        selectedTrailerKey = trailer.key
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                    },
                    modifier = Modifier
                        .padding(top = 18.sdp, bottom = 0.sdp),
                )

                if(selectedTrailerKey != null) {
                    TrailerScreen(
                        trailerKey = selectedTrailerKey!!,
                        onDismiss = {
                            selectedTrailerKey = null
                            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
                    )
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