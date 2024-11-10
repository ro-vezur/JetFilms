package com.example.jetfilms.CustomComposables

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import com.example.jetfilms.extensions.sdp

@Composable
fun NeonCard(
    glowingColor: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    cornersRadius: Dp = 0.sdp,
    glowingRadius: Dp = 20.sdp,
    xShifting: Dp = 0.sdp,
    yShifting: Dp = 0.sdp,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .drawBehind {
                val canvasSize = size
                drawContext.canvas.nativeCanvas.apply {
                    drawRoundRect(
                        0f, // Left
                        0f, // Top
                        canvasSize.width, // Right
                        canvasSize.height, // Bottom
                        cornersRadius.toPx(), // Radius X
                        cornersRadius.toPx(), // Radius Y
                        Paint().apply {
                            color = containerColor.toArgb()
                            isAntiAlias = true
                            setShadowLayer(
                                glowingRadius.toPx(),
                                xShifting.toPx(), yShifting.toPx(),
                                glowingColor.copy(alpha = 0.85f).toArgb()
                            )
                        }
                    )
                }
            }
    ) {
        content()
    }
}

@Composable
fun NeonButton(
    glowingColor: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    cornersRadius: Dp = 0.sdp,
    glowingRadius: Dp = 20.sdp,
    xShifting: Dp = 0.sdp,
    yShifting: Dp = 0.sdp,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .drawBehind {
                val canvasSize = size
                drawContext.canvas.nativeCanvas.apply {
                    drawRoundRect(
                        0f, // Left
                        0f, // Top
                        canvasSize.width, // Right
                        canvasSize.height, // Bottom
                        cornersRadius.toPx(), // Radius X
                        cornersRadius.toPx(), // Radius Y
                        Paint().apply {
                            color = containerColor.toArgb()
                            isAntiAlias = true
                            setShadowLayer(
                                glowingRadius.toPx(),
                                xShifting.toPx(), yShifting.toPx(),
                                glowingColor.copy(alpha = 0.85f).toArgb()
                            )
                        }
                    )
                }
            }
            .clickable { onClick() }
    ) {
        content()
    }
}