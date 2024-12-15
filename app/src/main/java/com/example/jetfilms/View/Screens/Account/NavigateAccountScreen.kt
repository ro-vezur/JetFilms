package com.example.jetfilms.View.Screens.Account

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetfilms.View.Screens.Account.Screens.AccountMainInfoScreen
import com.example.jetfilms.View.Screens.Account.Screens.ContactFormScreen
import com.example.jetfilms.View.Screens.Account.Screens.ReChooseInterests.reChooseInterestsNavHost
import com.example.jetfilms.View.Screens.AccountScreenNavigationHostRoute
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore

fun NavGraphBuilder.accountNavigationHost(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val turnBack = { navController.popBackStackOrIgnore() }

    navigation<AccountScreenNavigationHostRoute>(
        startDestination = AccountScreenNavigationHostRoute.AccountInfoScreenRoute
    ) {
        composable<AccountScreenNavigationHostRoute.AccountInfoScreenRoute> {
            AccountMainInfoScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable<AccountScreenNavigationHostRoute.EditAccountScreenRoute> {

        }

        reChooseInterestsNavHost(
            navController = navController
        )

        composable<AccountScreenNavigationHostRoute.ContactFormScreenRoute> {
            val user by userViewModel.user.collectAsStateWithLifecycle()

            user?.let { checkedUser ->
                ContactFormScreen(
                    user = checkedUser,
                    turnBack = turnBack
                )
            }
        }

        composable<AccountScreenNavigationHostRoute.AboutAppScreenRoute> {
            
        }

    }
}