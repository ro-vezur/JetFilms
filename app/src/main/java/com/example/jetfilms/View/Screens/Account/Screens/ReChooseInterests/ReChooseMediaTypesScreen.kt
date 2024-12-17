package com.example.jetfilms.View.Screens.Account.Screens.ReChooseInterests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.View.Components.Buttons.AcceptMultipleSelectionButton
import com.example.jetfilms.View.Components.Cards.MediaFormatCard
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun ReChooseMediaTypesScreen(
    usedMediaCategories: List<MediaCategories>,
    turnBack: () -> Unit,
    acceptNewMediaCategories: (mediaCategories: List<MediaCategories>) -> Unit,
    ) {
    val hazeState = remember { HazeState() }

    val selectedMediaCategories = remember{ mutableStateListOf<MediaCategories>() }

    LaunchedEffect(null) {
        selectedMediaCategories.addAll(usedMediaCategories)
    }

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
                modifier = Modifier
                    .hazeChild(hazeState),
                headerText = "Re-Choose Types",
                turnBack = turnBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = HAZE_STATE_BLUR.sdp,
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(19.sdp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 90.sdp)
            ) {
                MediaCategories.entries.forEach { format ->
                    val isSelected = selectedMediaCategories.contains(format)
                    MediaFormatCard(
                        mediaFormat = format,
                        selected = isSelected,
                        onClick = {
                            if (isSelected) {
                                selectedMediaCategories.remove(format)
                            } else {
                                selectedMediaCategories.add(format)
                            }
                        }
                    )
                }
            }

            AcceptMultipleSelectionButton(
                isEmpty = selectedMediaCategories.isEmpty(),
                isDataSameAsBefore = selectedMediaCategories.toList().sorted() == usedMediaCategories.sorted(),
                onAcceptText = "Accept New Types",
                onClick = { acceptNewMediaCategories(selectedMediaCategories) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 20).sdp)
            )
        }
    }
}