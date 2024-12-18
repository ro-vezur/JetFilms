package com.example.jetfilms.View.Screens.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.View.Components.Cards.SerialCard
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
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
fun MoreSerialsScreen(
    turnBack: () -> Unit,
    selectSeries: (id: Int) -> Unit,
    category: String,
    moreSeriesView: LazyPagingItems<SimplifiedSerialObject>
) {

    val gridState = rememberForeverLazyGridState(category)
    val hazeState = remember{HazeState()}

    val topBarHeight = 46
    val itemsGridSpacing = 9

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
    ) {  _ ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
            Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = HAZE_STATE_BLUR.sdp,
                )
        ){

            when {
                moreSeriesView.loadState.refresh is LoadState.Loading -> {
                    CircularProgressIndicator()
                }

                else -> LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(itemsGridSpacing.sdp),
                    verticalArrangement = Arrangement.spacedBy(itemsGridSpacing.sdp),
                    contentPadding = PaddingValues(8.sdp),
                    state = gridState,

                    ) {
                    item(span = { GridItemSpan(maxLineSpan) }) { Spacer(modifier = Modifier.height((topBarHeight-itemsGridSpacing).sdp)) }

                    items(moreSeriesView.itemCount) { index ->
                        val serial = moreSeriesView[index]
                        serial?.let {
                            SerialCard(
                                serial = serial,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.sdp))
                                    .height(205.sdp)
                                    .clickable { selectSeries(serial.id) }
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