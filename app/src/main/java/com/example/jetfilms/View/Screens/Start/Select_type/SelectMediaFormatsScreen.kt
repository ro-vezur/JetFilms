package com.example.jetfilms.View.Screens.Start.Select_type

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.View.Screens.Start.SelectMediaGenresScreenRoute
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.View.Components.Cards.MediaFormatCard
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2


@Composable
fun SelectMediaFormatScreen(
    stepsNavController: NavController,
    user: User,
    setUser: (user: User) -> Unit,
) {
    val typography = MaterialTheme.typography

    val selectedFormats = remember{ mutableStateListOf<MediaCategories>() }

    LaunchedEffect(null) {
        Log.d("user",user.toString())
        user.run {
            selectedFormats.addAll(recommendedMediaFormats)
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((BASE_BUTTON_HEIGHT + 28).sdp)
            ){
                TextButton(
                    onClick = {
                        if(selectedFormats.isNotEmpty()){
                            stepsNavController.navigate(SelectMediaGenresScreenRoute)
                        }
                    },
                    text = if(selectedFormats.isEmpty()) "Select at Least 1" else "Next",
                    textStyle = typography.bodyMedium.copy(
                        color = if(selectedFormats.isEmpty()) Color.Black else Color.White
                    ),
                    corners = RoundedCornerShape(12.sdp),
                    gradient = Brush.horizontalGradient(
                       if(selectedFormats.isEmpty()) listOf(Color.White, Color.White)
                       else listOf(buttonsColor1, buttonsColor2)
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    ) { innerPaddingValues ->
        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize()
        ) {

            TurnBackButton(
                onClick = { stepsNavController.navigateUp() },
                background = Color.LightGray.copy(0.6f),
                iconColor = Color.White,
                modifier = Modifier
                    .padding(start = 16.sdp, top = 34.sdp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.sdp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ){
                Text(
                    text = "Pick What You'd Like to Watch",
                    style = typography.titleLarge.copy(
                        fontSize = typography.titleLarge.fontSize * 1.09,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 22.sdp)
                        .fillMaxWidth(0.82f)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(19.sdp),
                    modifier = Modifier
                        .padding(bottom = 10.sdp)
                ) {
                    MediaCategories.entries.forEach { format ->
                        val isSelected = selectedFormats.contains(format)
                        MediaFormatCard(
                            mediaFormat = format,
                            selected = isSelected,
                            onClick = {
                                if (isSelected) {
                                    selectedFormats.remove(format)
                                    setUser(
                                        user.copy(recommendedMediaFormats = selectedFormats)
                                    )
                                } else {
                                    selectedFormats.add(format)
                                    setUser(
                                        user.copy(recommendedMediaFormats = selectedFormats)
                                    )
                                }

                            }
                        )
                    }
                }
            }
        }
    }
}
