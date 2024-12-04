package com.example.jetfilms.Components.TabsContent

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetfilms.DTOs.SeriesPackage.SerialDisplay
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialObject
import com.example.jetfilms.DTOs.TrailersResponse.TrailerObject
import com.example.jetfilms.DTOs.UnifiedDataPackage.SimplifiedParticipantResponse
import com.example.jetfilms.Screens.DetailedMediaScreens.SerialDetailsPackage.SerialAboutScreen
import com.example.jetfilms.Screens.DetailedMediaScreens.SerialDetailsPackage.SerialMoreLikeThisScreen
import com.example.jetfilms.Screens.DetailedMediaScreens.SerialDetailsPackage.SeriesTrailersScreen

@Composable
fun SeriesAboutTab(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    seriesDisplay: SerialDisplay,
    selectSeries: (id: Int) -> Unit,
    navigateToSelectedParticipant: (participant: SimplifiedParticipantResponse) -> Unit,
    selectTrailer: (trailer: TrailerObject) -> Unit
) {
    val seriesResponse = seriesDisplay.response

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = false
    ) { index ->
        when (index) {
            0 -> {
                SeriesTrailersScreen(
                    seriesDisplay = seriesDisplay,
                    selectTrailer = selectTrailer
                    )
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