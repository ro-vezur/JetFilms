package com.example.jetfilms.Screens.Favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.jetfilms.DTOs.Filters.Filter
import com.example.jetfilms.Screens.FavoriteScreen
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.states.rememberForeverScrollState

@Composable
fun FavoriteMainScreen(
    navController: NavController,
) {
    val scrollState = rememberForeverScrollState(key = "favorite screen")

    val screensNavigateButtons = listOf(
        "Download",
        "Favorite Movies and Series",
    )
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.sdp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 12.sdp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Divider(
            color = Color.DarkGray.copy(0.5f),
            thickness = 2f.sdp,
            modifier = Modifier
                .padding(top = 32.sdp)
                .fillMaxWidth()
                .clip(CircleShape)
        )
        
        screensNavigateButtons.forEach { text ->
            CategoryCard(text = text) {
                
            }
        }
    }
}

@Composable
private fun CategoryCard(
    text: String,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.sdp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.sdp))
            .clickable { onClick() }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 5.sdp)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                fontSize = 17.ssp,
                fontWeight = FontWeight.W400,
                color = Color.White,
                modifier = Modifier
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "forward",
                tint = Color.DarkGray.copy(0.5f),
                modifier = Modifier
                    .size(22.sdp)
            )
        }

        Divider(
            color = Color.DarkGray.copy(0.5f),
            thickness = 2f.sdp,
            modifier = Modifier
                .padding(bottom = 6.sdp)
                .fillMaxWidth()
                .clip(CircleShape)
        )
    }
}