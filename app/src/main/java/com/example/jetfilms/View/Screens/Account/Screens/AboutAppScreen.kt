package com.example.jetfilms.View.Screens.Account.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetfilms.APP_DESCRIPTION
import com.example.jetfilms.View.Components.TopBars.BaseTopAppBar
import com.example.jetfilms.extensions.popBackStackOrIgnore
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor

@Composable
fun AboutAppScreen(
    navController: NavController,
) {

    val turnBack = { navController.popBackStackOrIgnore() }

    Scaffold (
        containerColor = primaryColor,
        topBar = {
            BaseTopAppBar(
                headerText = "About JetFilms",
                turnBack = turnBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() + 50.sdp),
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 9.sdp),
                text = APP_DESCRIPTION,
                style = typography().bodyMedium,
                color = whiteColor
            )
        }
    }
}