package com.example.jetfilms.CustomComposables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.jetfilms.extensions.sdp

@Composable
fun Title(text:String) {
    val typography = MaterialTheme.typography

    Text(
        text = text,
        style = typography.titleLarge.copy(
            fontSize = typography.titleLarge.fontSize * 1.15,
            fontWeight = FontWeight.SemiBold
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(bottom = 30.sdp)
            .fillMaxWidth(0.8f)
    )
}