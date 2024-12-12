package com.example.jetfilms.View.Screens.SearchScreen.SearchScreenComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.View.Components.Cards.UnifiedCard
import com.example.jetfilms.extensions.sdp

@Composable
fun FilteredResultsLazyColumn(
    modifier: Modifier = Modifier,
    results: LazyPagingItems<UnifiedMedia>,
    selectMedia: (media: UnifiedMedia) -> Unit,
    topPadding: Dp = 0.sdp
) {

    when {
        results.loadState.refresh is LoadState.Loading -> {
            CircularProgressIndicator()
        }

        else -> LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(7.sdp),
            verticalArrangement = Arrangement.spacedBy(8.sdp),
            contentPadding = PaddingValues(horizontal = 0.sdp),
            userScrollEnabled = true,
        ) {

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(
                    modifier = Modifier.height(topPadding)
                )
            }

            items(results.itemCount) { index ->
                val filteredMedia = results[index]

                filteredMedia?.let {
                    UnifiedCard(
                        unifiedMedia = filteredMedia,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.sdp))
                            .height(155.sdp)
                            .clickable {selectMedia(filteredMedia) }
                    )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(BOTTOM_NAVIGATION_BAR_HEIGHT.sdp))
            }
        }
    }
}