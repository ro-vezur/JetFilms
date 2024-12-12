package com.example.jetfilms.View.Components.Bottom_Navigation_Bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetfilms.View.Components.Gradient.GradientIcon
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.BOTTOM_NAVIGATION_ITEM_SIZE
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild


@Composable
fun BottomNavBar(
    navController: NavController,
    hazeState: HazeState,
    showBottomBar: Boolean,
) {
    var selected by remember { mutableStateOf(BottomNavItems.HOME) }

    AnimatedVisibility(visible = showBottomBar){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .height(BOTTOM_NAVIGATION_BAR_HEIGHT.sdp)
                .hazeChild(state = hazeState)
        ) {
            BottomNavItems.entries.forEach { item ->
                BottomNavItem(
                    item = item,
                    selectedItem = selected,
                    onClick = {
                        selected = item
                        navController.navigate(item.route)
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(item: BottomNavItems,selectedItem: BottomNavItems, onClick: () -> Unit) {

    val isSelected = item == selectedItem

    var gradientPoint1 by remember{ mutableStateOf(Color.White.copy(0.82f)) }
    var gradientPoint2 by remember{ mutableStateOf(Color.White.copy(0.82f)) }

    val animatedGradientPoint1 = animateColorAsState(targetValue = gradientPoint1, tween(310))
    val animatedGradientPoint2 = animateColorAsState(targetValue = gradientPoint2, tween(200,120))

    LaunchedEffect(selectedItem) {
        if(isSelected){
            gradientPoint1 = buttonsColor1
            gradientPoint2 = buttonsColor2
        }else{
            gradientPoint1 = Color.White.copy(0.82f)
            gradientPoint2 = Color.White.copy(0.82f)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.sdp),
        modifier = Modifier
            .padding(top = 3.sdp)
            .width(48.sdp)
            .clip(RoundedCornerShape(7.sdp))
            .clickable {
                if (!isSelected) { onClick() }
            }
    ) {
        if(item == BottomNavItems.ACCOUNT){
            Icon(
                imageVector = item.icon,
                contentDescription = "nav icon",
                modifier = Modifier
                    .size(BOTTOM_NAVIGATION_ITEM_SIZE.sdp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(
                                Brush.horizontalGradient(
                                    listOf(
                                        animatedGradientPoint1.value,
                                        animatedGradientPoint2.value
                                    )
                                ),
                                blendMode = BlendMode.SrcAtop
                            )
                        }
                    },
            )
        }else{
            GradientIcon(
                icon = item.icon,
                contentDescription = "nav icon",
                gradient = Brush.horizontalGradient(
                    listOf(
                        animatedGradientPoint1.value,
                        animatedGradientPoint2.value
                    )
                ),
                modifier = Modifier
                    .size(BOTTOM_NAVIGATION_ITEM_SIZE.sdp)
            )
        }

        Text(
            text = item.title,
            fontSize = 13f.ssp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(
                brush =  Brush.horizontalGradient(listOf(animatedGradientPoint1.value,animatedGradientPoint2.value))
            ),
            modifier = Modifier
                .padding(bottom = 2.sdp)
        )
    }
}