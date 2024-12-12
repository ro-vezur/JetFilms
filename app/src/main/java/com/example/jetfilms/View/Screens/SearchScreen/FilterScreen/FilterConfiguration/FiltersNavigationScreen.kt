package com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterConfiguration

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.View.Screens.ExploreScreen
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.FilterViewModel
import com.example.jetfilms.ViewModels.SharedFilterConfigurationViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore

/*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun FiltersMainScreen(
    navController: NavHostController,
    turnOffFilter: () -> Unit,
    filterViewModel: FilterViewModel,
) {
    val hazeState = remember { HazeState() }

    val currentRoute by navController.currentBackStackEntryAsState()
    val usedGenres = filterViewModel.genresFilter.collectAsStateWithLifecycle()
    val usedCategories = filterViewModel.categories.collectAsStateWithLifecycle()
    val usedCountries = filterViewModel.filteredCountries.collectAsStateWithLifecycle()

    var genresToSelect by remember{ mutableStateOf(usedGenres.value) }
    var categoriesToSelect by remember{ mutableStateOf(usedCategories.value) }
    var countriesToSelect by remember{ mutableStateOf(usedCountries.value) }

    val turnBack = {navController.popBackStackOrIgnore()}

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            FiltersTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FILTER_TOP_BAR_HEIGHT.sdp)
                    .hazeChild(hazeState),
                turnBack = {
                    when(currentRoute?.destination?.route.toString()){
                        "Filters" -> turnOffFilter()
                        else -> turnBack()
                    }
                },
                reset = {
                    turnOffFilter()

                    filterViewModel.setSelectedSort(null)
                    filterViewModel.setFilteredGenres(BASE_MEDIA_GENRES)
                    filterViewModel.setFilteredCategories(MediaFormats.entries.toList())
                    filterViewModel.setFilteredCountries(getCountryList())
                        },
                text = currentRoute?.destination?.route.toString(),
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

 */

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.filterNavGraph(
    navController: NavHostController,
    filterViewModel: FilterViewModel,
    sharedFilterConfigurationViewModel: SharedFilterConfigurationViewModel,
) {
    navigation<ExploreScreen.FilterConfiguration>(
        startDestination = ExploreScreen.FilterConfiguration.AcceptFilters
    ){
        val turnBack = {
            navController.popBackStackOrIgnore()
        }

        fun resetFilter() {
            sharedFilterConfigurationViewModel.setSortType(null)
            sharedFilterConfigurationViewModel.setGenres(BASE_MEDIA_GENRES)
            sharedFilterConfigurationViewModel.setCategories(MediaFormats.entries.toList())
            sharedFilterConfigurationViewModel.setCountries(getCountryList())

            filterViewModel.setSelectedSort(null)
            filterViewModel.setFilteredGenres(BASE_MEDIA_GENRES)
            filterViewModel.setFilteredCategories(MediaFormats.entries.toList())
            filterViewModel.setFilteredCountries(getCountryList())
        }

        composable<ExploreScreen.FilterConfiguration.AcceptFilters> {
            val selectedSort by sharedFilterConfigurationViewModel.selectedSort.collectAsStateWithLifecycle()
            val genresToSelect by sharedFilterConfigurationViewModel.genresToSet.collectAsStateWithLifecycle()
            val categoriesToSelect by sharedFilterConfigurationViewModel.categoriesToSet.collectAsStateWithLifecycle()
            val countriesToSelect by sharedFilterConfigurationViewModel.countriesToSet.collectAsStateWithLifecycle()

            AcceptFiltersScreen(
                turnBack = turnBack,
                resetFilters = { resetFilter() },
                selectSortType = { newSortType -> sharedFilterConfigurationViewModel.setSortType(newSortType)},
                navController = navController,
                selectedSortType = selectedSort,
                genresToSelect = genresToSelect,
                categoriesToSelect = categoriesToSelect,
                countriesToSelect = countriesToSelect,
                acceptNewFilters = {
                    navController.navigate(ExploreScreen.FilteredResults)

                    filterViewModel.setSelectedSort(selectedSort)
                    filterViewModel.setFilteredGenres(genresToSelect)
                    filterViewModel.setFilteredCategories(categoriesToSelect)
                    filterViewModel.setFilteredCountries(countriesToSelect)

                    filterViewModel.setFilteredMedia(
                        sortType = selectedSort,
                        categories = categoriesToSelect,
                        countries = countriesToSelect,
                        genres = genresToSelect,
                    )
                },
            )
        }

        composable<ExploreScreen.FilterConfiguration.FilterGenres> {
            val genresToSelect by sharedFilterConfigurationViewModel.genresToSet.collectAsStateWithLifecycle()

            FilterGenresScreen(
                turnBack = turnBack,
                resetFilters = {resetFilter()},
                usedGenres = genresToSelect,
                acceptNewGenres = { genres ->
                    sharedFilterConfigurationViewModel.setGenres(genres)
                },
            )
        }

        composable<ExploreScreen.FilterConfiguration.FilterCategories> {
            val categoriesToSelect by sharedFilterConfigurationViewModel.categoriesToSet.collectAsStateWithLifecycle()

            FilterCategoriesScreen(
                turnBack = turnBack,
                resetFilters = { resetFilter() },
                usedCategories = categoriesToSelect,
                acceptNewCategories = { categories ->
                    sharedFilterConfigurationViewModel.setCategories(categories)
                }
            )
        }

        composable<ExploreScreen.FilterConfiguration.FilterCountries> {
            val countriesToSelect by sharedFilterConfigurationViewModel.countriesToSet.collectAsStateWithLifecycle()

            FilterCountriesScreen(
                turnBack = turnBack,
                resetFilters = {resetFilter()},
                usedCountries = countriesToSelect,
                acceptNewCountries = { countries ->
                    sharedFilterConfigurationViewModel.setCountries(countries)
                }
            )
        }

    }
}
