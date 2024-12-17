package com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterConfiguration

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.jetfilms.View.Components.Cards.MediaFormatCard
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.View.Components.Buttons.AcceptMultipleSelectionButton
import com.example.jetfilms.View.Components.TopBars.FiltersTopBar
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.primaryColor

@Composable
fun FilterCategoriesScreen(
    turnBack: () -> Unit,
    resetFilters: () -> Unit,
    usedCategories: List<MediaCategories>,
    acceptNewCategories: (genres:List<MediaCategories>) -> Unit,
) {

    val itemsSpacing = 15

    val selectedCategories = remember{ mutableStateListOf<MediaCategories>() }

    LaunchedEffect(null) {
        selectedCategories.clear()
        selectedCategories.addAll(usedCategories)
    }

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            FiltersTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FILTER_TOP_BAR_HEIGHT.sdp),
                turnBack = {turnBack() },
                reset = resetFilters,
                text = "Categories",
            )
        }
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 90.sdp)
            ) {

                items(MediaCategories.entries) { format ->
                    val selected = selectedCategories.contains(format)
                    MediaFormatCard(
                        mediaFormat = format,
                        selected = selected,
                        onClick = {
                            if (selected) {
                                selectedCategories.remove(format)
                            } else {
                                selectedCategories.add(format)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height((itemsSpacing).sdp))
                }
            }

            AcceptMultipleSelectionButton(
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
}