package com.example.jetfilms.View.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.jetfilms.View.Components.Gradient.GradientIcon
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.typography

@Composable
fun NoConnectionScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 10.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(9.sdp)
        ) {
            GradientIcon(
                modifier = Modifier
                    .size(92.sdp),
                icon = Icons.Filled.SignalWifiConnectedNoInternet4,
                contentDescription = "no internet connection",
                gradient = blueHorizontalGradient
            )

            Text(
                text = "No Internet Connection!\nPlease Check Your Internet Connection",
                style = typography().headlineLarge,
                textAlign = TextAlign.Center,
                lineHeight = 25.ssp
            )
        }
    }
}