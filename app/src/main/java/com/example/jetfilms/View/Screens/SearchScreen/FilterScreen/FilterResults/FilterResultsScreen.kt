package com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterResults

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Components.TopBars.FiltersTopBar
import com.example.jetfilms.View.Screens.SearchScreen.SearchScreenComponents.FilterButton
import com.example.jetfilms.View.Screens.SearchScreen.SearchScreenComponents.FilteredResultsLazyColumn
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.launch

@Composable
fun FilteredResultsScreen(
    filteredResults: LazyPagingItems<UnifiedMedia>,
    turnBack: () -> Unit,
    reset: () -> Unit,
    selectMedia: (id: Int, type: MediaFormats) -> Unit,
    onFilterButtonClick: () -> Unit,
) {
    val hazeState = remember{ HazeState() }

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            FiltersTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FILTER_TOP_BAR_HEIGHT.sdp)
                    .hazeChild(hazeState),
                turnBack = turnBack,
                text = "Discover",
                reset = reset,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = HAZE_STATE_BLUR.sdp,
                )
        ){
            FilteredResultsLazyColumn(
                modifier = Modifier
                    .padding(horizontal = 7.sdp)
                    .fillMaxSize(),
                results = filteredResults,
                selectMedia = { filteredMedia ->
                    selectMedia(
                        filteredMedia.id,
                        filteredMedia.mediaType
                    )
                },
                topPadding = FILTER_TOP_BAR_HEIGHT.sdp
            )

            FilterButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                onClick = onFilterButtonClick
            )
        }
    }
}