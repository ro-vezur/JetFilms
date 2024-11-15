package com.example.jetfilms.Bottom_Navigation_Bar

import android.graphics.Shader
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetfilms.CustomComposables.GradientIcon
import com.example.jetfilms.blueGradient
import com.example.jetfilms.bottomNavBarHeight
import com.example.jetfilms.bottomNavItemSize
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.whiteGradient
import com.primex.core.blur.legacyBackgroundBlur
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild


@Composable
fun BottomNavBar(
    navController: NavController,
    hazeState: HazeState,
    showBottomBar: Boolean,
) {
    AnimatedVisibility(visible = showBottomBar){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomNavBarHeight.sdp)
                .hazeChild(state = hazeState)
        ) {
            BottomNavItems.entries.forEach { item ->
                BottomNavItem(item = item, navController)
            }
        }
    }
}

@Composable
private fun BottomNavItem(item:BottomNavItems,navController: NavController) {

    var selected by remember{ mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var gradientPoint1 by remember{ mutableStateOf(Color.White.copy(0.82f)) }
    var gradientPoint2 by remember{ mutableStateOf(Color.White.copy(0.82f)) }

    val animatedGradientPoint1 = animateColorAsState(targetValue = gradientPoint1, tween(310))
    val animatedGradientPoint2 = animateColorAsState(targetValue = gradientPoint2, tween(200,120))

    LaunchedEffect(navBackStackEntry) {
        val currentRoute = navBackStackEntry?.destination?.route.toString()
        val itemRoute = item.route.toString()

        if(BottomNavItems.entries.map { it.route }.contains(currentRoute)) {
            selected = currentRoute == itemRoute

            if(selected){
                gradientPoint1 = buttonsColor1
                gradientPoint2 = buttonsColor2
            }else{
                gradientPoint1 = Color.White.copy(0.82f)
                gradientPoint2 = Color.White.copy(0.82f)
            }
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
                navController.navigate(item.route.toString())
            }
    ) {
        if(item == BottomNavItems.ACCOUNT){
            Icon(
                imageVector = item.icon,
                contentDescription = "nav icon",
                modifier = Modifier
                    .size(bottomNavItemSize.sdp)
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
                    .size(bottomNavItemSize.sdp)
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