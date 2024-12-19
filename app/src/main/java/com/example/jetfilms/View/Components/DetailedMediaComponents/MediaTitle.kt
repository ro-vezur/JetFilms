package com.example.jetfilms.View.Components.DetailedMediaComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.jetfilms.ui.theme.typography

@Composable
fun MediaTitle(text: String) {
    Text(
        text = text,
        style = typography().titleLarge,
    )
}