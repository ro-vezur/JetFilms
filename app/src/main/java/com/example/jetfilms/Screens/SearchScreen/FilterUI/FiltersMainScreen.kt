package com.example.jetfilms.Screens.SearchScreen.FilterUI

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Components.TopBars.FiltersTopBar
import com.example.jetfilms.DTOs.MoviePackage.MoviesResponse
import com.example.jetfilms.DTOs.SeriesPackage.SimplifiedSerialsResponse
import com.example.jetfilms.HAZE_STATE_BLUR
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.MoviesViewModel
import com.example.jetfilms.ViewModels.SeriesViewModel
import com.example.jetfilms.ViewModels.UnifiedMediaViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.hazeStateBlurBackground
import com.example.jetfilms.ui.theme.hazeStateBlurTint
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.typography
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun FiltersMainScreen(
    turnOffFilter: () -> Unit,
    moviesViewModel: MoviesViewModel,
    seriesViewModel: SeriesViewModel,
    unifiedMediaViewModel: UnifiedMediaViewModel,
) {
    val typography = typography()

    val navController = rememberNavController()
    val hazeState = remember { HazeState() }

    val currentRoute by navController.currentBackStackEntryAsState()
    val usedGenres = unifiedMediaViewModel.genresFilter.collectAsStateWithLifecycle()
    val usedCategories = unifiedMediaViewModel.categories.collectAsStateWithLifecycle()
    val usedCountries = unifiedMediaViewModel.filteredCountries.collectAsStateWithLifecycle()

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

                    unifiedMediaViewModel.showFilteredData(false)
                    unifiedMediaViewModel.setSelectedSort(null)
                    unifiedMediaViewModel.setFilteredGenres(BASE_MEDIA_GENRES)
                    unifiedMediaViewModel.setFilteredCategories(MediaFormats.entries.toList())
                    unifiedMediaViewModel.setFilteredCountries(getCountryList())
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
                    unifiedMediaViewModel = unifiedMediaViewModel,
                    genresToSelect = genresToSelect,
                    categoriesToSelect = categoriesToSelect,
                    countriesToSelect = countriesToSelect,
                    acceptNewFilters = { sortBy ->
                        unifiedMediaViewModel.setFilteredUnifiedData(
                            getMoviesResponse = { page ->
                                moviesViewModel.discoverMovies(
                                    page = page,
                                    sortBy =  sortBy?.requestQuery.toString(),
                                    genres =  genresToSelect.map { it.genreId },
                                    countries = countriesToSelect,
                                )
                            },
                            getSerialsResponse = { page ->
                                seriesViewModel.discoverSerials(
                                    page = page,
                                    sortBy =  sortBy?.requestQuery.toString(),
                                    genres =  genresToSelect.map { it.genreId },
                                    countries = countriesToSelect,
                                )
                            },
                            sortType = sortBy,
                            categories = categoriesToSelect
                        )
                    },
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