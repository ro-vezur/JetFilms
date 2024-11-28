package com.example.jetfilms.Screens.SearchScreen.FilterUI

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.jetfilms.Components.Cards.MediaFormatCard
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Components.Buttons.AcceptFiltersButton
import com.example.jetfilms.extensions.sdp

@Composable
fun FilterCategoriesScreen(
    turnBack: () -> Unit,
    usedCategories: List<MediaFormats>,
    acceptNewCategories: (genres:List<MediaFormats>) -> Unit,
) {

    val itemsSpacing = 15

    val selectedCategories = remember{ mutableStateListOf<MediaFormats>() }

    LaunchedEffect(null) {
        selectedCategories.clear()
        selectedCategories.addAll(usedCategories)
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
                    selectedFormats = selectedCategories
                )

                Spacer(modifier = Modifier.height((itemsSpacing).sdp))
            }
        }

        AcceptFiltersButton(
            isEmpty = selectedCategories.isEmpty(),
            isDataSameAsBefore = selectedCategories.toList().sorted() == usedCategories,
            onClick = {
                turnBack()
                acceptNewCategories(selectedCategories)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 9).sdp)
        )
    }
}