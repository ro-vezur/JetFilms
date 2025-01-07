package com.example.jetfilms.View.Screens.Account.Screens.ReChooseInterests

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetfilms.View.Screens.AccountScreenNavHost
import com.example.jetfilms.ViewModels.SharedInterestsConfigurationViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore

fun NavGraphBuilder.reChooseInterestsNavHost(
    navController: NavController,
) {

    val turnBack = { navController.popBackStackOrIgnore() }

    navigation<AccountScreenNavHost.ReChooseInterestNavHost>(
        startDestination = AccountScreenNavHost.ReChooseInterestNavHost.ChooseInterestsToChangeRoute
    ) {
        composable<AccountScreenNavHost.ReChooseInterestNavHost.ChooseInterestsToChangeRoute> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AccountScreenNavHost.ReChooseInterestNavHost)
            }

            val sharedInterestsConfigurationViewModel: SharedInterestsConfigurationViewModel = hiltViewModel(parentEntry)

            ChooseInterestToChangeScreen(
                navController = navController,
                turnBack = turnBack,
                acceptChanges = {
                    sharedInterestsConfigurationViewModel.setUser()
                    turnBack()
                }
            )
        }

        composable<AccountScreenNavHost.ReChooseInterestNavHost.MediaGenresRoute> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AccountScreenNavHost.ReChooseInterestNavHost)
            }

            val sharedInterestsConfigurationViewModel: SharedInterestsConfigurationViewModel = hiltViewModel(parentEntry)

            val usedMediaGenres by sharedInterestsConfigurationViewModel.mediaGenres.collectAsStateWithLifecycle()

            ReChooseMediaGenresScreen(
                usedMediaGenres = usedMediaGenres,
                turnBack = turnBack,
                acceptNewMediaGenres = { newMediaGenres ->
                    sharedInterestsConfigurationViewModel.setMediaGenres(newMediaGenres)
                    turnBack()
                }
            )
        }

        composable<AccountScreenNavHost.ReChooseInterestNavHost.MediaTypesRoute> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AccountScreenNavHost.ReChooseInterestNavHost)
            }

            val sharedInterestsConfigurationViewModel: SharedInterestsConfigurationViewModel = hiltViewModel(parentEntry)

            val usedMediaCategories by sharedInterestsConfigurationViewModel.mediaCategories.collectAsStateWithLifecycle()

            ReChooseMediaTypesScreen(
                usedMediaCategories = usedMediaCategories,
                turnBack = turnBack,
                acceptNewMediaCategories = { newMediaCategories ->
                    sharedInterestsConfigurationViewModel.setMediaCategories(newMediaCategories)
                    turnBack()
                }
            )
        }
    }
}