package com.example.jetfilms.View.Screens.Account

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetfilms.R
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.secondaryColor

@Composable
fun AccountMainInfoScreen(
    userViewModel: UserViewModel
) {
    val userFlow = userViewModel.user.collectAsStateWithLifecycle()
    Log.d("user flow",userFlow.value.toString())
    userFlow.value?.let { user ->
        Text(text = user.firstName, fontSize = 20.ssp)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextButton(
            onClick = {
                userViewModel.logOut()
            },
            text = "Log out",
            corners = RoundedCornerShape(12.sdp),
            background = secondaryColor,
            border = BorderStroke(
                1.45f.sdp, blueHorizontalGradient
            )
        )
    }
}