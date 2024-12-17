package com.example.jetfilms.View.Components.Buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.jetfilms.View.Components.Gradient.animatedGradient
import com.example.jetfilms.blueGradientColors
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor
import com.example.jetfilms.whiteGradientColors

@Composable
fun AcceptMultipleSelectionButton(
    modifier: Modifier = Modifier,
    isEmpty: Boolean,
    isDataSameAsBefore: Boolean,
    additionalText: String = "Select at Least 1",
    onAcceptText: String = "Accept Filters",
    onClick: () -> Unit,
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
            additionalText
        },
        textStyle = typography.bodyMedium,
        textColor = if(haveChanges) whiteColor else Color.Black,
        modifier = modifier
    )
}