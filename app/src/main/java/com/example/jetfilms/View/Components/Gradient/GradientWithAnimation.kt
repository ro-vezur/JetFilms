package com.example.jetfilms.View.Components.Gradient

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.jetfilms.Models.Enums.AnimatedGradientTypes

@Composable
fun animatedGradient(
    colors:List<Color>,
    type: AnimatedGradientTypes = AnimatedGradientTypes.HORIZONTAL,
    animationSpec: AnimationSpec<Color> = tween(durationMillis = 220, delayMillis = 35)
): Brush{

    return when(type){
        AnimatedGradientTypes.HORIZONTAL -> Brush.horizontalGradient(colors = colors.map { animateColorAsState(targetValue = it,animationSpec).value })
        AnimatedGradientTypes.VERTICAL -> Brush.verticalGradient(colors = colors.map { animateColorAsState(targetValue = it,animationSpec).value })
    }

}