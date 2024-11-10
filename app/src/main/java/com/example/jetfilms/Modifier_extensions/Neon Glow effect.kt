package com.example.jetfilms.Modifier_extensions

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetfilms.ui.theme.buttonsColor1

fun DrawScope.drawNeonStroke(radius: Dp,cornersRadius: Dp,color: Color) {
    val canvasSize = size
    drawContext.canvas.nativeCanvas.apply {
        drawRoundRect(
            0f,
            0f,
            canvasSize.width, // Right
            canvasSize.height, // Bottom
            cornersRadius.toPx(), // Radius X
            cornersRadius.toPx(), // Radius Y
            Paint().apply {
                isAntiAlias = true
                setShadowLayer(
                    radius.toPx(),
                    0f,
                    0f,
                    color.copy(alpha = 0.85f).toArgb()
                )
            }
        )
    }
}