package com.example.jetfilms.View.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.jetfilms.View.Components.Cards.NeonCard
import com.example.jetfilms.View.Components.Gradient.animatedGradient
import com.example.jetfilms.Models.Enums.AnimatedGradientTypes
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor

@Composable
fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: MutableState<Int>,
    modifier: Modifier = Modifier
) {

    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex.value, divider = {},
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            if (selectedTabIndex.value < tabPositions.size) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.sdp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(0.42f))
                    )

                    NeonCard(
                        glowingColor = buttonsColor1,
                        containerColor = buttonsColor2,
                        cornersRadius = Int.MAX_VALUE.sdp,
                        glowingRadius = 7.sdp,
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                            .height(2.sdp)
                    )
                }
            }
        }
    ) {
        tabs.forEachIndexed { index, title ->
            val selected = selectedTabIndex.value == index

            var selectedColor1 by remember{ mutableStateOf(buttonsColor1) }
            var selectedColor2 by remember{ mutableStateOf(buttonsColor2) }

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
                                type = AnimatedGradientTypes.VERTICAL
                            ),
                            fontSize = 11.ssp
                        )
                    )
                },
                selectedContentColor = buttonsColor1,
                unselectedContentColor = primaryColor,
                selected = selected,
                onClick = { selectedTabIndex.value = index },
                modifier = Modifier
            )
        }
    }
}