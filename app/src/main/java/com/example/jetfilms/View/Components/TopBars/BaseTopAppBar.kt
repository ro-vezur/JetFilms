package com.example.jetfilms.View.Components.TopBars

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@Composable
fun BaseTopAppBar(
    modifier: Modifier = Modifier,
    headerText: String,
    turnBack: () -> Unit
) {
    val typography = typography()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(FILTER_TOP_BAR_HEIGHT.sdp)

    ) {
        TurnBackButton(
            iconColor = whiteColor,
            size = 22.sdp,
            iconSize = 22.sdp,
            onClick = {
                turnBack()
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.sdp)
        )

        Text(
            text = headerText,
            color = whiteColor,
            style = typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 8.sdp)
        )
    }
}