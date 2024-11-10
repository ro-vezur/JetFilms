package com.example.jetfilms.CustomComposables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun animatedGradient(
    colors:List<Color>,
    type:String = "horizontal",
    animationSpec: AnimationSpec<Color> = tween(durationMillis = 220, delayMillis = 35)
): Brush{

    return when(type){
        "horizontal" -> Brush.horizontalGradient(colors = colors.map { animateColorAsState(targetValue = it,animationSpec).value })
        "vertical" -> Brush.verticalGradient(colors = colors.map { animateColorAsState(targetValue = it,animationSpec).value })
        else -> Brush.horizontalGradient(colors = colors.map { animateColorAsState(targetValue = it,animationSpec).value })
    }

}