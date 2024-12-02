package com.example.jetfilms.Components.TabsContent

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetfilms.DTOs.SeriesPackage.SerialDisplay
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.DTOs.UnifiedDataPackage.SimplifiedParticipantResponse
import com.example.jetfilms.Screens.SerialDetailsPackage.SerialAboutScreen
import com.example.jetfilms.Screens.SerialDetailsPackage.SerialMoreLikeThisScreen

@Composable
fun SeriesAboutTab(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    seriesDisplay: SerialDisplay,
    selectSeries: (id: Int) -> Unit,
    navigateToSelectedParticipant: (participant: SimplifiedParticipantResponse) -> Unit
) {
    val seriesResponse = seriesDisplay.response

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = false
    ) { index ->
        when (index) {
            0 -> {

            }

            1 -> {
                SerialMoreLikeThisScreen(
                    similarSeries = seriesDisplay.similarSerials,
                    selectSerial = selectSeries,
                )
            }

            2 -> {
                SerialAboutScreen(
                    seriesDisplay = seriesDisplay,
                    navigateToSelectedParticipant = navigateToSelectedParticipant,
                )
            }
        }
    }
}