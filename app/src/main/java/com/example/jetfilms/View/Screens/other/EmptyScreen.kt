package com.example.jetfilms.View.Screens.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = "history",
            tint = whiteColor,
            modifier = Modifier
                .size(55.sdp)
        )

        Text(
            text = text,
            style = typography().bodyMedium,
            fontWeight = FontWeight.W400,
            color = whiteColor,
            modifier = Modifier
                .padding(top = 8.sdp)
        )
    }
}