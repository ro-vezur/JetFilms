package com.example.jetfilms.Screens.Favorite

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Components.TopBars.FiltersTopBar
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.ViewModels.SeriesViewModel
import com.example.jetfilms.ViewModels.UnifiedMediaViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore
import com.example.jetfilms.ui.theme.primaryColor
import dev.chrisbanes.haze.HazeState

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteNavigateScreen(
    moviesViewModel: MoviesViewModel,
    seriesViewModel: SeriesViewModel,
    unifiedMediaViewModel: UnifiedMediaViewModel,
) {

    val navController = rememberNavController()
    val hazeState = remember{HazeState()}

    val currentRoute by navController.currentBackStackEntryAsState()

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
                    navController = navController
                )
            }
        }
    }

}