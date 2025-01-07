package com.example.jetfilms.View.Screens.Favorite

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Components.LazyComponents.LazyGrid.UnifiedMediaVerticalLazyGrid
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun FavoriteMediaListScreen(
    turnBack: () -> Unit,
    selectMedia: (unifiedMedia: UnifiedMedia) -> Unit,
    favoriteMediaList: List<UnifiedMedia>,
) {

    val hazeState = remember {HazeState()}

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
                modifier = Modifier
                    .hazeChild(hazeState),
                headerText = "Favorite Media",
                turnBack = turnBack,
            )
        }
    ) { innerPadding ->
        UnifiedMediaVerticalLazyGrid(
            modifier = Modifier
                .padding(horizontal = 6.sdp)
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = HAZE_STATE_BLUR.sdp,
                ),
            topPadding = (BASE_BUTTON_HEIGHT + 3).sdp,
            data = favoriteMediaList.toList(),
            selectMedia = selectMedia
        )
    }
}