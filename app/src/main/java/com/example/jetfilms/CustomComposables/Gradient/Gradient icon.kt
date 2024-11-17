package com.example.jetfilms.CustomComposables.Gradient

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetfilms.bottomNavItemSize
import com.example.jetfilms.extensions.sdp

@Composable
fun GradientIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String,
    gradient: Brush,
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        gradient,
                        blendMode = BlendMode.SrcAtop
                    )
                }
            }
    )
}