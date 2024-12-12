package com.example.jetfilms.View.Screens.SearchScreen.SearchScreenComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.primaryColor

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 25).sdp)
            .width(90.sdp)
            .height(30.sdp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable {onClick() }
    ) {
        Icon(
            imageVector = Icons.Filled.FilterList,
            tint = primaryColor,
            contentDescription = "filter",
            modifier = Modifier
                .padding(start = 10.sdp)
                .size(22.sdp)
        )

        Text(
            text = "Filters",
            color = primaryColor,
            fontSize = 14.ssp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 4.sdp)
        )
    }
}