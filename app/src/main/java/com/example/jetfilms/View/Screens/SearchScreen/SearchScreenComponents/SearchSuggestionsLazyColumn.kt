package com.example.jetfilms.View.Screens.SearchScreen.SearchScreenComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.Helpers.removeNumbersAfterDecimal
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.R
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.whiteColor

@Composable
fun SearchSuggestionsLazyColumn(
    searchSuggestions: List<UnifiedMedia>,
    onSuggestionClick: ( suggestion: UnifiedMedia) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.sdp),
        modifier = Modifier
            .padding(top = (FILTER_TOP_BAR_HEIGHT + 12).sdp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.sdp))
            .background(primaryColor)
    ) {
        items(searchSuggestions) { suggestion ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 5.sdp)
                    .fillMaxWidth(0.96f)
                    .heightIn(25.sdp, 50.sdp)
                    .clickable { onSuggestionClick(suggestion) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = whiteColor,
                    modifier = Modifier
                        .padding(start = 7.sdp)
                        .size(22.sdp)
                )

                Text(
                    text = suggestion.title,
                    color = whiteColor,
                    fontSize = 13.ssp,
                    lineHeight = 17.ssp,
                    modifier = Modifier
                        .padding(start = 7.sdp)
                        .fillMaxWidth(0.75f)
                )

                Image(
                    painter = painterResource(id = R.drawable.imdb_logo_2016_svg),
                    contentDescription = "imdb",
                    modifier = Modifier
                        .padding(start = 10.sdp)
                        .width(22.sdp)
                )

                Text(
                    text = removeNumbersAfterDecimal(
                        suggestion.rating,
                        numbersAfterDecimal = 1
                    ).toString(),
                    color = whiteColor,
                    fontSize = 12.ssp,
                    lineHeight = 17.ssp,
                    modifier = Modifier
                        .padding(start = 8.sdp)
                )
            }
        }
    }
}