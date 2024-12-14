package com.example.jetfilms.View.Screens.Favorite

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.ViewModels.FavoriteMediaViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore
import com.example.jetfilms.ui.theme.primaryColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteNavigateScreen(
    searchedHistoryFlow: List<UnifiedMedia>,
    searchedHistoryInDb: List<SearchedMedia>,
    selectMedia: (unifiedMedia: UnifiedMedia) -> Unit,
    user: User,
) {
    val favoriteMediaViewModel = hiltViewModel<FavoriteMediaViewModel,FavoriteMediaViewModel.FavoriteMediaViewModelFactory> { factory ->
        factory.create(user)
    }

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
                FavoriteMainScreen(
                    navController = navController,
                    searchedHistoryFlow = searchedHistoryFlow,
                    searchedHistoryInDb = searchedHistoryInDb,
                    selectMedia = selectMedia
                )
            }

            composable("Favorite Movies and Series") {


                val favoriteMediaList by favoriteMediaViewModel.favoriteMediaList.collectAsStateWithLifecycle()

                Log.d("user media list size",user.favoriteMediaList.size.toString())


                LaunchedEffect(null){ favoriteMediaViewModel.setFavoriteMedia(user.favoriteMediaList) }

                FavoriteMediaListScreen(
                    turnBack = turnBack,
                    selectMedia = selectMedia,
                    favoriteMediaList = favoriteMediaList
                )
            }


            composable("Download") {

            }
        }
    }

}