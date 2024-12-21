package com.example.jetfilms.View.Screens.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Components.Cards.UnifiedCard
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.View.states.rememberForeverLazyGridState
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoreUnifiedScreen(
    turnBack: () -> Unit,
    selectMedia: (id: Int,category: MediaCategories) -> Unit,
    category: String,
    moreUnifiedMediaView: LazyPagingItems<UnifiedMedia>,
) {

    val gridState = rememberForeverLazyGridState(category)
    val hazeState = remember{HazeState()}

    val topBarHeight = 52.sdp

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
                modifier = Modifier
                    .hazeChild(hazeState),
                headerText = category,
                turnBack = turnBack
            )
        },
        modifier = Modifier
    ) { _ ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = HAZE_STATE_BLUR.sdp,
                    noiseFactor = 0f
                )
        ){
            when {
                moreUnifiedMediaView.loadState.refresh is LoadState.Loading -> {
                    CircularProgressIndicator()
                }
                else ->  LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(9.sdp),
                    verticalArrangement = Arrangement.spacedBy(9.sdp),
                    contentPadding = PaddingValues(8.sdp),
                    state = gridState,

                    ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Spacer(
                            modifier = Modifier.height(
                                topBarHeight
                            )
                        )
                    }

                    items(moreUnifiedMediaView.itemCount) { index ->
                        val unifiedMedia = moreUnifiedMediaView[index]
                        unifiedMedia?.let {
                            UnifiedCard(
                                unifiedMedia = unifiedMedia,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.sdp))
                                    .height(205.sdp)
                                    .clickable {
                                        selectMedia(unifiedMedia.id,unifiedMedia.mediaCategory)
                                    }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.size((BOTTOM_NAVIGATION_BAR_HEIGHT - 6).sdp))
                    }
                }
            }
        }
    }
}