package com.example.jetfilms.View.Screens.Account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetfilms.View.Screens.Account.Screens.AboutAppScreen
import com.example.jetfilms.View.Screens.Account.Screens.AccountMainInfoScreen
import com.example.jetfilms.View.Screens.Account.Screens.ContactFormScreen
import com.example.jetfilms.View.Screens.Account.Screens.EditProfileScreen.EditProfileScreen
import com.example.jetfilms.View.Screens.Account.Screens.ReChooseInterests.reChooseInterestsNavHost
import com.example.jetfilms.View.Screens.AccountScreenNavHost
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore

fun NavGraphBuilder.accountNavigationHost(
    navController: NavController,
    showBottomBar: (show: Boolean) -> Unit,
) {
    val turnBack = { navController.popBackStackOrIgnore() }

    navigation<AccountScreenNavHost>(
        startDestination = AccountScreenNavHost.AccountInfoRoute
    ) {
        showBottomBar(true)
        composable<AccountScreenNavHost.AccountInfoRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(AccountScreenNavHost)
            }

            val userViewModel: UserViewModel = hiltViewModel(parentEntry)
            val user by userViewModel.user.collectAsStateWithLifecycle()

            user?.let { checkedUser ->
                AccountMainInfoScreen(
                    navController = navController,
                    user = checkedUser,
                    logOut = { userViewModel.logOut() }
                )
            }
        }

        composable<AccountScreenNavHost.EditAccountRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(AccountScreenNavHost)
            }

            val userViewModel: UserViewModel = hiltViewModel(parentEntry)
            val user by userViewModel.user.collectAsStateWithLifecycle()

            user?.let { checkedUser ->
                EditProfileScreen(
                    user = checkedUser,
                    turnBack = turnBack,
                    saveUserChanges = { newUser ->
                        turnBack()
                        userViewModel.addOrUpdateUser(
                            checkedUser.copy(
                                firstName = newUser.firstName,
                                lastName = newUser.lastName,
                                password = newUser.password
                            )
                        )
                    },
                    updatePassword = { email, oldPassword, newPassword ->
                        userViewModel.updatePassword(email, oldPassword, newPassword)
                    }
                )
            }
        }


        reChooseInterestsNavHost(
            navController = navController,
        )

        composable<AccountScreenNavHost.ContactFormRoute> {backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(AccountScreenNavHost)
            }

            val userViewModel: UserViewModel = hiltViewModel(backstackEntry)
            val user by userViewModel.user.collectAsStateWithLifecycle()

            user?.let { checkedUser ->
                ContactFormScreen(
                    user = checkedUser,
                    turnBack = turnBack
                )
            }
        }

        composable<AccountScreenNavHost.AboutAppRoute> {
            AboutAppScreen(
                navController = navController,

            )
        }

    }
}