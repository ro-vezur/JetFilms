package com.example.jetfilms.CustomComposables

import android.bluetooth.BluetoothClass.Device
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.jetfilms.extensions.sdp
import com.primex.core.blur.legacyBackgroundBlur
import com.primex.core.noise

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun TurnBackButton(
    onClick:() -> Unit,
    size: Dp = 30.sdp,
    shape: RoundedCornerShape = RoundedCornerShape(100),
    background: Color = Color.Transparent,
    iconColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
          //  .legacyBackgroundBlur(radius = 1f, downsample = 0.3f)
            .background(background)
           // .noise(0.01f)
            .clickable { onClick() }
    ){

        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "turn back",
            tint = iconColor,
            modifier = Modifier
                .align(Alignment.Center)
                .size(size / 1.5f)
        )


    }
}

@RequiresApi(Build.VERSION_CODES.S)
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