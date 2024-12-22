package com.example.jetfilms.View.Components.Buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.jetfilms.extensions.sdp

@Composable
fun TurnBackButton(
    onClick:() -> Unit,
    size: Dp = 31.sdp,
    iconSize: Dp = size / 1.4f,
    shape: RoundedCornerShape = RoundedCornerShape(100),
    background: Color = Color.Transparent,
    iconColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(background)
            .clickable { onClick() }
    ){

        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "turn back",
            tint = iconColor,
            modifier = Modifier
                .align(Alignment.Center)
                .size(iconSize)
        )


    }
}

@Preview(
)
@Composable
private fun asfasf() {
    TurnBackButton(
        background = Color.LightGray.copy(0.55f),
        iconColor = Color.White,
        onClick = {},
        )
}