package com.example.jetfilms.Screens.Home

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetfilms.Components.Buttons.TurnBackButton
import com.example.jetfilms.Components.Cards.SerialCard
import com.example.jetfilms.Helpers.navigate.navigateToSelectedSerial
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.states.rememberForeverLazyGridState
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MoreSerialsScreen(
    selectSeries: (series: SimplifiedSerialObject) -> Unit,
    navController: NavController,
    category: String,
    moviesViewModel: MoviesViewModel
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    val gridState = rememberForeverLazyGridState(category)
    val hazeState = remember{HazeState()}
    val scope = rememberCoroutineScope()

    val moreSerialsView = moviesViewModel.moreSerialsView.collectAsLazyPagingItems()

    val topBarHeight = 46
    val itemsGridSpacing = 9

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            Box(
                modifier = Modifier
                    .height(topBarHeight.sdp)
                    .hazeChild(state = hazeState)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 6.sdp)
                        .fillMaxWidth()

                ) {
                    TurnBackButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        iconColor = Color.White,
                        size = 29.sdp,
                        modifier = Modifier
                            .padding(start = 12.sdp)
                    )

                    Text(
                        text = category,
                        style = typography.headlineLarge,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.size(29.sdp))
                }
            }
        },
        modifier = Modifier
    ) { innerPadding ->

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
                moreSerialsView.loadState.refresh is LoadState.Loading -> {
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

                    items(moreSerialsView.itemCount) { index ->
                        val serial = moreSerialsView[index]
                        serial?.let {
                            SerialCard(
                                serial = serial,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.sdp))
                                    .height(205.sdp)
                                    .clickable { selectSeries(serial) }
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