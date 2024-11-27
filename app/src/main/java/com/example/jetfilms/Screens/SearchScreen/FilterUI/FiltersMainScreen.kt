package com.example.jetfilms.Screens.SearchScreen.FilterUI

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Components.Buttons.TurnBackButton
import com.example.jetfilms.Components.TopBars.FiltersTopBar
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun FiltersMainScreen(
     turnOffFilter: () -> Unit,
     moviesViewModel: MoviesViewModel,
) {
    val typography = typography()

    val navController = rememberNavController()
    val hazeState = remember { HazeState() }

    val currentRoute by navController.currentBackStackEntryAsState()
    val usedGenres = moviesViewModel.genresFilter.collectAsStateWithLifecycle()
    val usedCategories = moviesViewModel.categories.collectAsStateWithLifecycle()
    val usedCountries = moviesViewModel.filteredCountries.collectAsStateWithLifecycle()

    var genresToSelect by remember{ mutableStateOf(usedGenres.value) }
    var categoriesToSelect by remember{ mutableStateOf(usedCategories.value) }
    var countriesToSelect by remember{ mutableStateOf(usedCountries.value) }

    val turnBack = {navController.popBackStackOrIgnore()}

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            FiltersTopBar(
                turnBack = {
                    when(currentRoute?.destination?.route.toString()){
                        "Filters" -> turnOffFilter()
                        else -> turnBack()
                    }
                },
                reset = {
                    turnOffFilter()

                    moviesViewModel.showFilteredData(false)
                    moviesViewModel.setSelectedSort(null)
                    moviesViewModel.setFilteredGenres(BASE_MEDIA_GENRES)
                    moviesViewModel.setFilteredCategories(MediaFormats.entries.toList())
                        },
                text = currentRoute?.destination?.route.toString(),
                hazeState = hazeState
            )
        }
    ) {  innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Filters",
            modifier = Modifier
                .haze(
                    hazeState,
                    backgroundColor = hazeStateBlurBackground,
                    tint = hazeStateBlurTint,
                    blurRadius = HAZE_STATE_BLUR.sdp,
                )
        ) {
            composable("Filters"){
                AcceptFiltersScreen(
                    turnBack = turnOffFilter,
                    navController = navController,
                    moviesViewModel = moviesViewModel,
                    genresToSelect = genresToSelect,
                    categoriesToSelect = categoriesToSelect,
                    countriesToSelect = countriesToSelect
                )
            }

            composable(route = "Genres") {
                FilterGenresScreen(
                    turnBack = turnBack,
                    usedGenres = genresToSelect,
                    acceptNewGenres = { genres ->
                        genresToSelect = genres
                    },
                )
            }

            composable("Categories") {
                FilterCategoriesScreen(
                    turnBack = turnBack,
                    usedCategories = categoriesToSelect,
                    acceptNewCategories = { categories ->
                        categoriesToSelect = categories
                    }
                )
            }

            composable("Country") {
                FilterCountriesScreen(
                    turnBack = turnBack,
                    usedCountries = countriesToSelect,
                    acceptNewCountries = { countries ->
                        countriesToSelect = countries
                    }
                )
            }
        }
    }
}