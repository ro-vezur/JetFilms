package com.example.jetfilms.Screens.MovieDetailsPackage

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.jetfilms.Helpers.removeNumbersAfterDecimal
import com.example.jetfilms.Components.Gradient.GradientIcon
import com.example.jetfilms.Components.Cards.NeonCard
import com.example.jetfilms.Components.Buttons.TextButton
import com.example.jetfilms.Components.Buttons.TurnBackButton
import com.example.jetfilms.Components.Cards.EpisodeCard
import com.example.jetfilms.Components.Gradient.animatedGradient
import com.example.jetfilms.DTOs.MoviePackage.MovieDisplay
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.DTOs.ParticipantPackage.SimplifiedMovieParticipant
import com.example.jetfilms.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.DTOs.SeriesPackage.SerialSeasonResponse
import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.R
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.Helpers.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SerialDetailsScreen(
    navController: NavController,
    serialResponse: DetailedSerialResponse,
    selectSeason: suspend (serialId: Int,seasonNumber: Int) -> SerialSeasonResponse?
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val scrollState = rememberScrollState()
    val tabs = serialResponse.seasons
    val tabContentPagerState = rememberPagerState(pageCount = {tabs.size})
    val currentPage = tabContentPagerState.currentPage

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    var imageHeight by rememberSaveable{ mutableStateOf(290) }
    val scope = rememberCoroutineScope()

    var selectedSeason by remember { mutableStateOf<SerialSeasonResponse?>(null) }

    LaunchedEffect(currentPage) {
        Log.d("id",serialResponse.id.toString())
        selectedSeason =
           try {
               selectSeason(serialResponse.id,currentPage)
           } catch (e:Exception){
               selectSeason(serialResponse.id,currentPage+1)
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
                    model = "$BASE_IMAGE_API_URL${decodeStringWithSpecialCharacter(serialResponse.poster.toString())}",
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.sdp),
                        modifier = Modifier.padding(bottom = 5.sdp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.imdb_logo_2016_svg),
                            contentDescription = "IMDb",
                            modifier = Modifier
                                .width(30.sdp)
                                .clip(RoundedCornerShape(3.sdp))
                        )

                        Text(
                            text = removeNumbersAfterDecimal(serialResponse.rating,2).toString(),
                            fontSize = 21f.ssp
                        )
                    }

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
                        FilterCard(
                            text = DateFormats().year(serialResponse.releaseDate).toString(),
                            lengthMultiplayer = 13
                        )

                        if(serialResponse.genres.isNotEmpty()){
                            FilterCard(
                                text = serialResponse.genres.first().name,
                                lengthMultiplayer = 8
                            )
                        }

                        if(serialResponse.countries.isNotEmpty()){
                            FilterCard(
                                text = serialResponse.countries.first(),
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

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(12.sdp),
                modifier = Modifier
                    .padding(start = 14.sdp,end = 14.sdp,top = 16.sdp)
            ) {
                Text(
                    text = "${serialResponse.seasons.size} Season" + if(serialResponse.seasons.size == 1) "" else "s",
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

            ScrollableTabRow(
                modifier = Modifier
                    .padding(top = 8.sdp, start = 6.sdp, end = 6.sdp)
                    .fillMaxWidth(),
                edgePadding = 0.sdp,
                selectedTabIndex = tabContentPagerState.currentPage, divider = {},
                containerColor = Color.Transparent,
                indicator = { tabPositions ->
                    if (tabContentPagerState.currentPage < tabPositions.size) {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
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
                                    .tabIndicatorOffset(tabPositions[tabContentPagerState.currentPage])
                                    .height(2f.sdp)
                            )
                        }
                    }
                }
            ) {
                tabs.forEachIndexed { index, season ->
                    if(season.episodeCount != 0){
                        val selected = tabContentPagerState.currentPage == index

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
                                        if(serialResponse.seasons.first().seasonNumber == 0)season.seasonNumber+1
                                        else season.seasonNumber
                                    }",
                                    style = TextStyle(
                                        brush = animatedGradient(
                                            colors = listOf(selectedColor1, selectedColor2),
                                            type = "vertical"
                                        ),
                                        fontSize = 14f.ssp
                                    )
                                )
                            },
                            selectedContentColor = buttonsColor1,
                            unselectedContentColor = primaryColor,
                            selected = selected,
                            onClick = {
                                scope.launch { tabContentPagerState.animateScrollToPage(index) }
                            },
                            modifier = Modifier
                        )
                    }
                }
            }

            HorizontalPager(
                userScrollEnabled = false,
                state = tabContentPagerState,
                modifier = Modifier
                    .padding(top = 8.sdp)
                    .fillMaxWidth()
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(11.sdp)
                ) {
                    selectedSeason?.let {
                        it.episodes.forEach { episode ->  
                            EpisodeCard(
                                modifier = Modifier
                                    .padding(horizontal = 6.sdp)
                                    .fillMaxWidth()
                                    .height(100.sdp),
                                episode = episode
                            )
                        }
                    }
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

@Composable
private fun FilterCard(
    text:String,
    lengthMultiplayer: Int
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .width((text.length.sdp * lengthMultiplayer))
            .height(25.sdp)
            .clip(RoundedCornerShape(14.sdp))
            .background(colors.primary)
            .border(BorderStroke(1.5f.sdp, blueHorizontalGradient), RoundedCornerShape(14.sdp))
    ){
        Text(
            text = text,
            fontSize = 11.ssp,
            style = TextStyle(
                brush = blueHorizontalGradient
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun TabContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    movieDisplay: MovieDisplay,
    selectMovie: (movie: SimplifiedMovieDataClass) -> Unit,
    navigateToSelectedParticipant: (participant: SimplifiedMovieParticipant) -> Unit
) {
    val movieResponse = movieDisplay.response

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = false
    ) { index ->
        when (index) {
            0 -> {
                MovieTrailersScreen(movie = movieResponse)
            }

            1 -> {
                MovieMoreLikeThisScreen(
                    similarMovies = movieDisplay.similarMovies,
                    selectMovie = selectMovie
                )
            }

            2 -> {
                MovieAboutScreen(
                    movieDisplay = movieDisplay,
                    navigateToSelectedParticipant = navigateToSelectedParticipant
                )
            }
        }
    }
}