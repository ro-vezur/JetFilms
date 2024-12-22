package com.example.jetfilms.View.Screens.Account.Screens.ReChooseInterests

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.View.Screens.AccountScreenNavHost
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.whiteColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun ChooseInterestToChangeScreen(
    navController: NavController,
    turnBack: () -> Unit,
    acceptChanges: () -> Unit
) {
    Scaffold(
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
                modifier = Modifier,
                headerText = "Re-Choose Interests",
                turnBack = turnBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(25.sdp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 100.sdp)
            ) {
                CategoryNavigationButton(
                    text = "Media Types",
                    imageUrl = "https://cdn.mos.cms.futurecdn.net/qrGysRQqahBfSdaHyAd2D7-1200-80.jpg",
                    onClick = {
                        navController.navigate(AccountScreenNavHost.ReChooseInterestNavHost.MediaTypesRoute)
                    }
                )

                CategoryNavigationButton(
                    text = "Media Genres",
                    imageUrl = MediaGenres.ALL.imageUrl,
                    onClick = {
                        navController.navigate(AccountScreenNavHost.ReChooseInterestNavHost.MediaGenresRoute)
                    }
                )
            }

            TextButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = BOTTOM_NAVIGATION_BAR_HEIGHT.sdp + 20.sdp),
                onClick = { acceptChanges() },
                text = "Accept New Interests",
                corners = RoundedCornerShape(12.sdp),
                gradient = blueHorizontalGradient,
            )
        }
    }
}

@Composable
private fun CategoryNavigationButton(text: String,imageUrl: String, onClick: () -> Unit) {
    val hazeState = remember { HazeState() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(240.sdp)
            .height(130.sdp)
            .clip(RoundedCornerShape(10.sdp))
            .border(BorderStroke(2.sdp, blueHorizontalGradient), RoundedCornerShape(10.sdp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = 10.sdp,
                    noiseFactor = 10f
                )
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width((text.length*10).sdp)
                .height(37.sdp)
                .hazeChild(hazeState, shape = CircleShape)
        ) {
            Text(text = text, color = whiteColor)
        }
    }
}