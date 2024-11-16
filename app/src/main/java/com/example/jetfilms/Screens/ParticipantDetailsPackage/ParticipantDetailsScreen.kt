package com.example.jetfilms.Screens.ParticipantDetailsPackage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.jetfilms.CustomComposables.Buttons.TurnBackButton
import com.example.jetfilms.CustomComposables.Cards.NeonCard
import com.example.jetfilms.CustomComposables.Gradient.animatedGradient
import com.example.jetfilms.CustomComposables.Text.ExpandableText
import com.example.jetfilms.Data_Classes.MoviePackage.DetailedMovieDisplay
import com.example.jetfilms.Data_Classes.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.Data_Classes.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.baseImageUrl
import com.example.jetfilms.blueVerticalGradient
import com.example.jetfilms.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ParticipantDetailsScreen(
    navController: NavController,
    participantDisplay: DetailedParticipantDisplay,
    selectMovie: (movie: SimplifiedMovieDataClass) -> Unit
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

    var imageHeight by rememberSaveable{ mutableStateOf(290) }
    val scope = rememberCoroutineScope()

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
                    .height(animateIntAsState(targetValue = 342).value.sdp)
            ) {
                AsyncImage(
                    model = "$baseImageUrl${decodeStringWithSpecialCharacter(participantResponse.image.toString())}",
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

            TabRow(
                modifier = Modifier
                    .padding(top = 8.sdp, start = 6.sdp,end = 6.sdp),
                selectedTabIndex = tabPagerState.currentPage, divider = {},
                containerColor = Color.Transparent,
                indicator = { tabPositions ->
                    if (tabPagerState.currentPage < tabPositions.size) {
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
                                    .tabIndicatorOffset(tabPositions[tabPagerState.currentPage])
                                    .height(2f.sdp)
                            )


                        }
                    }
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    val selected = tabPagerState.currentPage == index

                    var selectedColor1 by remember{ mutableStateOf(buttonsColor1) }
                    var selectedColor2 by remember{ mutableStateOf(buttonsColor2) }

                    var unselectedColor by remember{ mutableStateOf(Color.DarkGray) }

                    if(selected){
                        selectedColor1 = buttonsColor1
                        selectedColor2 = buttonsColor2
                    } else{
                        selectedColor1 = Color.LightGray.copy(0.42f)
                        selectedColor2 = Color.LightGray.copy(0.42f)
                    }

                    Tab(
                        text = {
                            Text(
                                title,
                                style = TextStyle(
                                    brush = animatedGradient(
                                        colors = listOf(selectedColor1,selectedColor2),
                                        type = "vertical"
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

            TabContent(
                modifier = Modifier
                    .padding(top = 18.sdp, bottom = 0.sdp),
                pagerState = tabPagerState,
                participantDisplay = participantDisplay,
                selectMovie = selectMovie
            )
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
    selectMovie: (movie: SimplifiedMovieDataClass) -> Unit,
) {
    val participantResponse = participantDisplay.participantResponse

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = false
    ) { index ->
        when (index) {
            0 -> {

            }

            1 -> {
                BiographyScreen(participantDisplay = participantDisplay)
            }
        }
    }
}