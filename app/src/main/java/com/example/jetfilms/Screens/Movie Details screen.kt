package com.example.jetfilms.Screens

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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.jetfilms.Additional_functions.fromMinutesToHours
import com.example.jetfilms.CustomComposables.GradientIcon
import com.example.jetfilms.CustomComposables.NeonButton
import com.example.jetfilms.CustomComposables.NeonCard
import com.example.jetfilms.CustomComposables.TextButton
import com.example.jetfilms.CustomComposables.TurnBackButton
import com.example.jetfilms.CustomComposables.animatedGradient
import com.example.jetfilms.Data_Classes.DetailedMovieDataClass
import com.example.jetfilms.Date_formats.DateFormats
import com.example.jetfilms.Modifier_extensions.drawNeonStroke
import com.example.jetfilms.R
import com.example.jetfilms.baseImageUrl
import com.example.jetfilms.blueGradient
import com.example.jetfilms.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.minutesInHour
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movie: DetailedMovieDataClass?
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val scrollState = rememberScrollState()

    var imageHeight by rememberSaveable{ mutableStateOf(290) }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        Log.d("current back stack entry",currentBackStackEntry?.destination?.route.toString())
        imageHeight = if(currentBackStackEntry?.destination?.route == "movie_details/{movie}"){
            335
        } else{
            290
        }
    }

    val tabs = listOf("Trailers","More Like This","About")
    var selectedTabIndex by remember{ mutableStateOf(0) }

    val neonRadius = 7.sdp

    movie?.let {
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
                        model = "$baseImageUrl${decodeStringWithSpecialCharacter(movie.posterUrl.toString())}",
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
                                text = movie.rating.toString(),
                                fontSize = 21f.ssp
                            )
                        }

                        Text(
                            text = decodeStringWithSpecialCharacter(movie.title),
                            style = typography.titleLarge,
                            fontSize = 26f.ssp,
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.sdp),
                            modifier = Modifier.padding(top = 14.sdp, start = 3.sdp, bottom = 6.sdp)
                        ) {
                            FilterCard(
                                text = DateFormats().year(movie.releaseDate).toString(),
                                lengthMultiplayer = 13
                            )

                            FilterCard(
                                text = movie.genres.first().name,
                                lengthMultiplayer = 8
                            )

                            FilterCard(
                                text = movie.originCountries.first(),
                                lengthMultiplayer = 21
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.sdp),
                            modifier = Modifier.padding(top = 18.sdp, start = 3.sdp, bottom = 6.sdp)
                        ) {
                            TextButton(
                                onClick = {

                                },
                                gradient = blueGradient,
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
                                        gradient = blueGradient,
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
                                        gradient = blueGradient,
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
                        text = fromMinutesToHours(movie.runtime),
                        fontSize = 15.ssp,
                        fontWeight = FontWeight.W500
                        )

                    Text(
                        text = decodeStringWithSpecialCharacter(movie.overview),
                        fontSize = 13.ssp,
                        color = Color.LightGray.copy(0.9f),
                        fontWeight = FontWeight.W400,
                        modifier = Modifier
                            .padding(start = 2.sdp)
                    )
                }

                TabRow(
                    modifier = Modifier
                        .padding(top = 8.sdp, start = 6.sdp,end = 6.sdp),
                    selectedTabIndex = selectedTabIndex, divider = {},
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        if (selectedTabIndex < tabPositions.size) {
                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxWidth()
                                  //  .background(Color.DarkGray)
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
                                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                        .height(2f.sdp)
                                )


                            }
                        }
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        val selected = selectedTabIndex == index

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
                            onClick = { selectedTabIndex = index },
                            modifier = Modifier
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
            .border(BorderStroke(1.5f.sdp, blueGradient), RoundedCornerShape(14.sdp))
    ){
        Text(
            text = text,
            fontSize = 11.ssp,
            style = TextStyle(
                brush = blueGradient
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}