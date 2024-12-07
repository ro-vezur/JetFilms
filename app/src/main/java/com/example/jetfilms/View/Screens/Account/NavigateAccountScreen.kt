package com.example.jetfilms.View.Screens.Account

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.ui.theme.primaryColor

@Composable
fun NavigateAccountScreen(
    userViewModel: UserViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        containerColor = primaryColor,
        modifier = Modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AccountScreensRoutes.MAIN_INFO_SCREEN.name
        ){

            composable(
                route = AccountScreensRoutes.MAIN_INFO_SCREEN.name
            ) {
                AccountMainInfoScreen(
                    userViewModel = userViewModel
                )
            }
        }
    }
}