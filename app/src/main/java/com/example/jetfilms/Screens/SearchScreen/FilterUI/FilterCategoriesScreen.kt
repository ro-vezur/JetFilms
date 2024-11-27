package com.example.jetfilms.Screens.SearchScreen.FilterUI

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.jetfilms.Components.Cards.MediaFormatCard
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Components.Buttons.TextButton
import com.example.jetfilms.Components.Cards.MediaGenreCard
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.states.rememberForeverLazyGridState
import dev.chrisbanes.haze.HazeState

@Composable
fun FilterCategoriesScreen(
    turnBack: () -> Unit,
    usedCategories: List<MediaFormats>,
    acceptNewCategories: (genres:List<MediaFormats>) -> Unit,
) {

    val itemsSpacing = 15

    val selectedFormats = remember{ mutableStateListOf<MediaFormats>() }

    LaunchedEffect(null) {
        selectedFormats.clear()
        selectedFormats.addAll(usedCategories)
        Log.d("used categories",usedCategories.toString())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 90.sdp)
        ) {

            items(MediaFormats.entries) { format ->
                MediaFormatCard(
                    mediaFormat = format,
                    selectedFormats = selectedFormats
                )

                Spacer(modifier = Modifier.height((itemsSpacing).sdp))
            }
        }

        TextButton(
            onClick = {
                turnBack()
                acceptNewCategories(selectedFormats)
            },
            gradient = blueHorizontalGradient,
            text = "Accept Filters",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 9).sdp)
        )

    }
}