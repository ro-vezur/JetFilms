package com.example.jetfilms.Components.Cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

@Composable
fun PropertyCard(
    text:String,
    lengthMultiplayer: Int
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .width((text.length.sdp * lengthMultiplayer))
            .height(25.sdp)
            .clip(RoundedCornerShape(14.sdp))
            .background(colors.primary)
            .border(BorderStroke(1.5f.sdp, blueHorizontalGradient), RoundedCornerShape(14.sdp))
    ){
        Text(
            text = text,
            fontSize = 11.ssp,
            style = TextStyle(
                brush = blueHorizontalGradient
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}