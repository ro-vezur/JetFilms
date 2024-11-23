package com.example.jetfilms.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

// Set of Material typography styles to start with

@Composable
fun typography():Typography {
    return Typography(
        bodyLarge = TextStyle(
            fontSize = 1.ssp,
         //   letterSpacing = 0.5f.ssp
        ),

        bodyMedium = TextStyle(
            fontSize = (19.1f).ssp,
            fontWeight = FontWeight.SemiBold
        ),

        bodySmall = TextStyle(
            fontSize = (12).ssp,
            fontWeight = FontWeight.W500
        ),

        titleLarge = TextStyle(
            fontSize = (22).ssp,
            fontWeight = FontWeight.W500,
            lineHeight = 32.ssp
        ),

        headlineLarge = TextStyle(
            fontSize = (20).ssp,
            fontWeight = FontWeight.Normal,
        )
    )
}
