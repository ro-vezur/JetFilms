package com.example.jetfilms.View.Screens.Favorite

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.ViewModels.FavoriteMediaViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore
import com.example.jetfilms.ui.theme.primaryColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteNavigateScreen(
    selectMedia: (unifiedMedia: UnifiedMedia) -> Unit,
    user: User,
) {
    val favoriteMediaViewModel = hiltViewModel<FavoriteMediaViewModel>()

    val navController = rememberNavController()

    val turnBack = {
        navController.popBackStackOrIgnore()
    }

    Scaffold(
        containerColor = primaryColor,
        topBar = {}
    ){
        NavHost(
            navController = navController,
            startDestination = "MainScreen"
        ) {
            composable("MainScreen") {

                val searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel()

                val searchedHistoryMediaData by searchHistoryViewModel.searchedMediaData.collectAsStateWithLifecycle()
                val searchedHistoryMediaIds by searchHistoryViewModel.searchedMediaIds.collectAsStateWithLifecycle()

                FavoriteMainScreen(
                    navController = navController,
                    searchedHistoryMediaDataResult = searchedHistoryMediaData,
                    searchedHistoryMediaIds = searchedHistoryMediaIds,
                    selectMedia = selectMedia
                )
            }

            composable("Favorite Movies and Series") {
                val favoriteMediaList by favoriteMediaViewModel.favoriteMediaList.collectAsStateWithLifecycle()

                LaunchedEffect(Unit){ favoriteMediaViewModel.setFavoriteMedia(user.favoriteMediaList) }

                FavoriteMediaListScreen(
                    turnBack = turnBack,
                    selectMedia = selectMedia,
                    favoriteMediaList = favoriteMediaList
                )
            }
        }
    }

}