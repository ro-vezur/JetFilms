package com.example.jetfilms.Components.Buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.Components.Gradient.animatedGradient
import com.example.jetfilms.blueGradientColors
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor
import com.example.jetfilms.whiteGradientColors

@Composable
fun AcceptFiltersButton(
    isEmpty: Boolean,
    isDataSameAsBefore: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val typography = typography()

    val haveChanges = !isEmpty && !isDataSameAsBefore

    TextButton(
        onClick = {
            if(!isEmpty){
                onClick()
            }
        },
        gradient = animatedGradient(colors = if(haveChanges) blueGradientColors else whiteGradientColors),
        text =
        if(!isEmpty)
            if(isDataSameAsBefore){
                "Back"
            } else {
                "Accept Filters"
            }
        else {
            "Select at Least 1"
        },
        textStyle = typography.bodyMedium.copy(
            color = if(haveChanges) whiteColor else Color.Black
        ),
        modifier = modifier
    )
}