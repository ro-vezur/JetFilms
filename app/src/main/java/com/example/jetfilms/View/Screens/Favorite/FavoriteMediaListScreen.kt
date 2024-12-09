package com.example.jetfilms.View.Screens.Favorite

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Components.LazyComponents.LazyGrid.UnifiedMediaVerticalLazyGrid
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun FavoriteMediaListScreen(
    turnBack: () -> Unit,
    selectMedia: (unifiedMedia: UnifiedMedia) -> Unit,
    data: List<UnifiedMedia>,
) {

    val hazeState = remember {HazeState()}

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
                headerText = "Favorite Media",
                turnBack = turnBack,
                hazeState = hazeState
            )
        }
    ) { innerPadding ->
        UnifiedMediaVerticalLazyGrid(
            data = data,
            selectMedia = selectMedia
        )
    }
}