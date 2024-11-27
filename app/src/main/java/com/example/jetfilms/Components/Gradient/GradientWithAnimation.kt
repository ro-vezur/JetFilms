package com.example.jetfilms.Components.Gradient

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.jetfilms.DTOs.animatedGradientTypes

@Composable
fun animatedGradient(
    colors:List<Color>,
    type: animatedGradientTypes = animatedGradientTypes.HORIZONTAL,
    animationSpec: AnimationSpec<Color> = tween(durationMillis = 220, delayMillis = 35)
): Brush{

    return when(type){
        animatedGradientTypes.HORIZONTAL -> Brush.horizontalGradient(colors = colors.map { animateColorAsState(targetValue = it,animationSpec).value })
        animatedGradientTypes.VERTICAL -> Brush.verticalGradient(colors = colors.map { animateColorAsState(targetValue = it,animationSpec).value })
    }

}