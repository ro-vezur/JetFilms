package com.example.jetfilms.View.Components.Cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .height(25.sdp)
            .clip(RoundedCornerShape(14.sdp))
            .background(colorScheme.primary)
            .border(BorderStroke(1.sdp, blueHorizontalGradient), RoundedCornerShape(14.sdp))
            .padding(horizontal = 12.sdp)
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