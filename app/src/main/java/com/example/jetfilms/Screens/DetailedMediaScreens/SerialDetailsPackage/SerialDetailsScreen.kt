package com.example.jetfilms.Screens.MovieDetailsPackage

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.jetfilms.Components.Gradient.GradientIcon
import com.example.jetfilms.Components.Cards.NeonCard
import com.example.jetfilms.Components.Buttons.TextButton
import com.example.jetfilms.Components.Buttons.TurnBackButton
import com.example.jetfilms.Components.Cards.EpisodeCard
import com.example.jetfilms.Components.Gradient.animatedGradient
import com.example.jetfilms.DTOs.SeriesPackage.SerialSeasonResponse
import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.Components.Cards.PropertyCard
import com.example.jetfilms.Components.DetailedMediaComponents.DisplayRating
import com.example.jetfilms.Components.MediaInfoTabRow
import com.example.jetfilms.Components.TabsContent.SeriesAboutTab
import com.example.jetfilms.DTOs.SeriesPackage.SerialDisplay
import com.example.jetfilms.DTOs.UnifiedDataPackage.SimplifiedParticipantResponse
import com.example.jetfilms.DTOs.animatedGradientTypes
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.Helpers.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.Screens.DetailedMediaScreens.TrailerScreen
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.infoTabs
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SerialDetailsScreen(
    navController: NavController,
    serialDisplay: SerialDisplay,
    selectSeason: suspend (serialId: Int,seasonNumber: Int) -> SerialSeasonResponse?,
    selectSerial: (id: Int) -> Unit,
    selectParticipant: (participant: SimplifiedParticipantResponse) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val serialResponse = serialDisplay.response

    val seasonTabs = serialResponse.seasons

    val seasonsPagerState = rememberPagerState(pageCount = {seasonTabs.size})
    val currentSeasonPage = seasonsPagerState.currentPage

    val infoPagerState = rememberPagerState(pageCount = {infoTabs.size})
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current

    val activity = context as Activity

    var imageHeight by rememberSaveable{ mutableStateOf(290) }
    val scope = rememberCoroutineScope()

    var selectedSeason by remember { mutableStateOf<SerialSeasonResponse?>(null) }
    var selectedTrailerKey by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(currentSeasonPage) {
        selectedSeason =
           try {
               selectSeason(serialResponse.id,currentSeasonPage)
           } catch (e:Exception){
               selectSeason(serialResponse.id,currentSeasonPage+1)
           }
    }

    LaunchedEffect(currentBackStackEntry) {
        imageHeight = if(currentBackStackEntry?.destination?.route == "serial_details/{serial}"){
            335
        } else{
            290
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
                .background(colors.primary)
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
                                serialResponse.poster.toString()
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
                        DisplayRating(serialResponse.rating)

                        Text(
                            text = decodeStringWithSpecialCharacter(serialResponse.name),
                            style = typography.titleLarge,
                            fontSize = 26f.ssp,
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.sdp),
                            modifier = Modifier.padding(top = 14.sdp, start = 3.sdp, bottom = 6.sdp)
                        ) {
                            if (serialResponse.releaseDate.isNotBlank()) {
                                PropertyCard(
                                    text = DateFormats().getYear(serialResponse.releaseDate)
                                        .toString(),
                                    lengthMultiplayer = 13
                                )
                            }

                            if (serialResponse.genres.isNotEmpty()) {
                                PropertyCard(
                                    text = serialResponse.genres.first().name,
                                    lengthMultiplayer = 8
                                )
                            }

                            if (serialResponse.originCountries.isNotEmpty()) {
                                PropertyCard(
                                    text = serialResponse.originCountries.first(),
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
                                        if (false) colors.secondary.copy(.85f)
                                        else colors.secondary.copy(.92f)
                                    )
                            ) {
                                if (false) {
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
                        text = "${serialResponse.seasons.size} Season" + if (serialResponse.seasons.size == 1) "" else "s",
                        fontSize = 16.ssp,
                        fontWeight = FontWeight.W500
                    )

                    Text(
                        text = decodeStringWithSpecialCharacter(serialResponse.overview),
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
                    selectedTabIndex = seasonsPagerState.currentPage, divider = {},
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        if (seasonsPagerState.currentPage < tabPositions.size) {
                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(2f.sdp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray.copy(0.42f))
                                )

                                NeonCard(
                                    glowingColor = buttonsColor1,
                                    containerColor = buttonsColor2,
                                    cornersRadius = Int.MAX_VALUE.sdp,
                                    glowingRadius = 7.sdp,
                                    modifier = Modifier
                                        .tabIndicatorOffset(tabPositions[seasonsPagerState.currentPage])
                                        .height(2f.sdp)
                                )
                            }
                        }
                    }
                ) {
                    seasonTabs.forEachIndexed { index, season ->
                        if (season.episodeCount != 0) {
                            val selected = seasonsPagerState.currentPage == index

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
                                            if (serialResponse.seasons.first().seasonNumber == 0) season.seasonNumber + 1
                                            else season.seasonNumber
                                        }",
                                        style = TextStyle(
                                            brush = animatedGradient(
                                                colors = listOf(selectedColor1, selectedColor2),
                                                type = animatedGradientTypes.VERTICAL
                                            ),
                                            fontSize = 14f.ssp
                                        )
                                    )
                                },
                                selectedContentColor = buttonsColor1,
                                unselectedContentColor = primaryColor,
                                selected = selected,
                                onClick = {
                                    scope.launch { seasonsPagerState.animateScrollToPage(index) }
                                },
                                modifier = Modifier
                            )
                        }
                    }
                }
            }

            item{
                HorizontalPager(
                    userScrollEnabled = false,
                    state = seasonsPagerState,
                    modifier = Modifier
                        .padding(top = 8.sdp)
                        .fillMaxWidth()
                ) {}
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
                MediaInfoTabRow(
                    tabs = infoTabs,
                    pagerState = infoPagerState,
                    modifier = Modifier
                        .padding(top = 12.sdp, start = 6.sdp, end = 6.sdp)
                )
            }

            item{
                SeriesAboutTab(
                    pagerState = infoPagerState,
                    navigateToSelectedParticipant = selectParticipant,
                    selectSeries = selectSerial,
                    seriesDisplay = serialDisplay,
                    selectTrailer = {trailer ->
                        selectedTrailerKey = trailer.key
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                    },
                    modifier = Modifier
                        .padding(top = 18.sdp, bottom = 0.sdp),
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
}