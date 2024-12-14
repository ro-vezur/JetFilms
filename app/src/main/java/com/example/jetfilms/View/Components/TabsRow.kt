package com.example.jetfilms.View.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.jetfilms.View.Components.Cards.NeonCard
import com.example.jetfilms.View.Components.Gradient.animatedGradient
import com.example.jetfilms.Models.DTOs.animatedGradientTypes
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import kotlinx.coroutines.launch

@Composable
fun TabRow(
    tabs: List<String>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    TabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage, divider = {},
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            if (pagerState.currentPage < tabPositions.size) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2f.sdp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(0.42f))
                    )

                    NeonCard(
                        glowingColor = buttonsColor1,
                        containerColor = buttonsColor2,
                        cornersRadius = Int.MAX_VALUE.sdp,
                        glowingRadius = 7.sdp,
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .height(2f.sdp)
                    )
                }
            }
        }
    ) {
        tabs.forEachIndexed { index, title ->
            val selected = pagerState.currentPage == index

            var selectedColor1 by remember{ mutableStateOf(buttonsColor1) }
            var selectedColor2 by remember{ mutableStateOf(buttonsColor2) }

            var unselectedColor by remember{ mutableStateOf(Color.DarkGray) }

            if(selected){
                selectedColor1 = buttonsColor1
                selectedColor2 = buttonsColor2
            } else{
                selectedColor1 = Color.LightGray.copy(0.42f)
                selectedColor2 = Color.LightGray.copy(0.42f)
            }

            Tab(
                text = {
                    Text(
                        title,
                        style = TextStyle(
                            brush = animatedGradient(
                                colors = listOf(selectedColor1,selectedColor2),
                                type = animatedGradientTypes.VERTICAL
                            ),
                            fontSize = 14.5f.ssp
                        )
                    )
                },
                selectedContentColor = buttonsColor1,
                unselectedContentColor = primaryColor,
                selected = selected,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                modifier = Modifier
            )
        }
    }
}