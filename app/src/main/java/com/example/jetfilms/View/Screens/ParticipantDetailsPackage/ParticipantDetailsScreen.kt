package com.example.jetfilms.View.Screens.ParticipantDetailsPackage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.View.Components.Cards.NeonCard
import com.example.jetfilms.View.Components.Gradient.animatedGradient
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.View.Components.Cards.MovieCard
import com.example.jetfilms.Models.DTOs.animatedGradientTypes
import com.example.jetfilms.Helpers.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import kotlinx.coroutines.launch

@Composable
fun ParticipantDetailsScreen(
    navController: NavController,
    participantDisplay: DetailedParticipantDisplay,
    selectMovie: (id: Int) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val tabs = listOf(
        "Filmography",
        "Biography",
    )

    val participantResponse = participantDisplay.participantResponse

    val scrollState = rememberScrollState()
    val tabPagerState = rememberPagerState(pageCount = {tabs.size})

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val filmographyColumns = 3f

    val scope = rememberCoroutineScope()

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
                            style = typography.titleLarge,
                            fontSize = 26f.ssp,
                        )

                    }
                }
            }

            item{
                TabRow(
                    modifier = Modifier
                        .padding(top = 8.sdp, start = 6.sdp, end = 6.sdp),
                    selectedTabIndex = tabPagerState.currentPage, divider = {},
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        if (tabPagerState.currentPage < tabPositions.size) {
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
                                        .tabIndicatorOffset(tabPositions[tabPagerState.currentPage])
                                        .height(2f.sdp)
                                )


                            }
                        }
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        val selected = tabPagerState.currentPage == index

                        var selectedColor1 by remember { mutableStateOf(buttonsColor1) }
                        var selectedColor2 by remember { mutableStateOf(buttonsColor2) }

                        var unselectedColor by remember { mutableStateOf(Color.DarkGray) }

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
                                    title,
                                    style = TextStyle(
                                        brush = animatedGradient(
                                            colors = listOf(selectedColor1, selectedColor2),
                                            type = animatedGradientTypes.VERTICAL
                                        ),
                                        fontSize = 14.5f.ssp
                                    )
                                )
                            },
                            selectedContentColor = buttonsColor1,
                            unselectedContentColor = primaryColor,
                            selected = selected,
                            onClick = { scope.launch { tabPagerState.animateScrollToPage(index) } },
                            modifier = Modifier
                        )
                    }
                }
            }

            item{
                HorizontalPager(
                    state = tabPagerState,
                    userScrollEnabled = false
                ){}
            }

            if(tabPagerState.currentPage == 0){
                items(participantDisplay.filmography.cast.sortedByDescending { it.popularity }.chunked(3)){ chunk ->
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

                AnimatedVisibility(visible = tabPagerState.currentPage == 1) {
                    BiographyScreen(participantDisplay = participantDisplay)
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
private fun TabContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    participantDisplay: DetailedParticipantDisplay,
    selectMovie: (id: Int) -> Unit,
) {
    val participantResponse = participantDisplay.participantResponse

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = false
    ) { index ->
        when (index) {
            0 -> {
                FilmographyScreen(
                    participantDisplay = participantDisplay,
                    selectMovie = selectMovie
                )
            }

            1 -> {
                BiographyScreen(participantDisplay = participantDisplay)
            }
        }
    }
}