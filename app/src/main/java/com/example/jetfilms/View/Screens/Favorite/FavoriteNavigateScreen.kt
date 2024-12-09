package com.example.jetfilms.View.Screens.Favorite

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ViewModels.SeriesViewModel
import com.example.jetfilms.ViewModels.UnifiedMediaViewModel
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteNavigateScreen(
    searchHistoryViewModel: SearchHistoryViewModel,
    unifiedMediaViewModel: UnifiedMediaViewModel,
    selectMedia: (unifiedMedia: UnifiedMedia) -> Unit
) {

    val navController = rememberNavController()

    val favoriteMediaList = unifiedMediaViewModel.favoriteMediaList.collectAsStateWithLifecycle()

    Log.d("favorite media list in work",favoriteMediaList.value.toString())

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
                FavoriteMainScreen(
                    navController = navController,
                    searchHistoryViewModel = searchHistoryViewModel,
                    selectMedia = selectMedia
                )
            }

            composable("Favorite Movies and Series") {
                FavoriteMediaListScreen(
                    turnBack = turnBack,
                    selectMedia = selectMedia,
                    data = favoriteMediaList.value
                )
            }

            composable("Download") {

            }
        }
    }

}