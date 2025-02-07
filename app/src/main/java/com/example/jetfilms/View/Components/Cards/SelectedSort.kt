package com.example.jetfilms.View.Components.Cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.secondaryColor
import com.example.jetfilms.ui.theme.transparentColor

@Composable
fun SortSelectedCard(
    text:String,
    selected: Boolean,
    onClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .height(27.sdp)
            .clip(RoundedCornerShape(14.sdp))
            .background( if(selected) secondaryColor.copy(0.75f) else Color.DarkGray.copy(0.85f))
            .border(
                BorderStroke(
                    1.sdp,
                    if(selected) blueHorizontalGradient
                    else Brush.horizontalGradient(listOf(transparentColor,transparentColor))
                ),
                RoundedCornerShape(14.sdp)
            )
            .padding(horizontal = 8.sdp)
            .clickable { onClick() }
    ){
        Text(
            text = text,
            fontSize = 12.ssp,
            fontWeight = FontWeight.W500,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}