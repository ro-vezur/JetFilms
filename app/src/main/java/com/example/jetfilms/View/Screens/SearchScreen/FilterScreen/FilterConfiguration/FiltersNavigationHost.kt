package com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterConfiguration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetfilms.View.Screens.ExploreNavigationHost
import com.example.jetfilms.ViewModels.FilterViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore


fun NavGraphBuilder.filterNavGraph(
    navController: NavController,
) {
    navigation<ExploreNavigationHost.FilterConfigurationNavigationHost>(
        startDestination = ExploreNavigationHost.FilterConfigurationNavigationHost.AcceptFiltersRoute
    ){
        val turnBack = {
            navController.popBackStackOrIgnore()
        }


        composable<ExploreNavigationHost.FilterConfigurationNavigationHost.AcceptFiltersRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(ExploreNavigationHost)
            }

            val filterViewModel: FilterViewModel = hiltViewModel(parentEntry)

            val selectedSort by filterViewModel.selectedSort.collectAsStateWithLifecycle()
            val genresToSelect by filterViewModel.genresFilter.collectAsStateWithLifecycle()
            val categoriesToSelect by filterViewModel.categoriesFilter.collectAsStateWithLifecycle()
            val countriesToSelect by filterViewModel.filteredCountries.collectAsStateWithLifecycle()
            val yearFilterToSet by filterViewModel.filteredYears.collectAsStateWithLifecycle()
            val yearFilterFrom by filterViewModel.filterFromYear.collectAsStateWithLifecycle()
            val yearFilterTo by filterViewModel.filterToYear.collectAsStateWithLifecycle()

            val yearsRangeMap = mapOf(
                "fromYear" to yearFilterFrom,
                "toYear" to yearFilterTo
            )

            AcceptFiltersScreen(
                turnBack = turnBack,
                resetFilters = { filterViewModel.resetFilters() },
                selectSortType = { newSortType -> filterViewModel.setSelectedSort(newSortType)},
                navController = navController,
                selectedSortType = selectedSort,
                genresToSelect = genresToSelect,
                categoriesToSelect = categoriesToSelect,
                countriesToSelect = countriesToSelect,
                yearsFilterToSelect = yearFilterToSet,
                yearsFilterRange = yearsRangeMap,
            ) {

                navController.navigate(ExploreNavigationHost.FilteredResultsRoute)

                filterViewModel.setSelectedSort(selectedSort)
                filterViewModel.setFilteredGenres(genresToSelect)
                filterViewModel.setFilteredCategories(categoriesToSelect)
                filterViewModel.setFilteredCountries(countriesToSelect)

                filterViewModel.setFilteredMedia(
                    sortType = selectedSort,
                    categories = categoriesToSelect,
                    countries = countriesToSelect,
                    genres = genresToSelect,
                    year = yearFilterToSet,
                    yearRange = yearsRangeMap
                )
            }
        }

        composable<ExploreNavigationHost.FilterConfigurationNavigationHost.FilterGenresRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(ExploreNavigationHost)
            }

            val filterViewModel: FilterViewModel = hiltViewModel(parentEntry)
            val genresToSelect by filterViewModel.genresFilter.collectAsStateWithLifecycle()

            FilterGenresScreen(
                turnBack = turnBack,
                resetFilters = { filterViewModel.resetFilters() },
                usedGenres = genresToSelect,
                acceptNewGenres = { genres ->
                    filterViewModel.setFilteredGenres(genres)
                },
            )
        }

        composable<ExploreNavigationHost.FilterConfigurationNavigationHost.FilterCategoriesRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(ExploreNavigationHost)
            }

            val filterViewModel: FilterViewModel = hiltViewModel(parentEntry)
            val categoriesToSelect by filterViewModel.categoriesFilter.collectAsStateWithLifecycle()

            FilterCategoriesScreen(
                turnBack = turnBack,
                resetFilters = { filterViewModel.resetFilters() },
                usedCategories = categoriesToSelect,
                acceptNewCategories = { categories ->
                    filterViewModel.setFilteredCategories(categories)
                }
            )
        }

        composable<ExploreNavigationHost.FilterConfigurationNavigationHost.FilterCountriesRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(ExploreNavigationHost)
            }

            val filterViewModel: FilterViewModel = hiltViewModel(parentEntry)
            val countriesToSelect by filterViewModel.filteredCountries.collectAsStateWithLifecycle()

            FilterCountriesScreen(
                turnBack = turnBack,
                resetFilters = {filterViewModel.resetFilters()},
                usedCountries = countriesToSelect,
                acceptNewCountries = { countries ->
                    filterViewModel.setFilteredCountries(countries)
                }
            )
        }

        composable<ExploreNavigationHost.FilterConfigurationNavigationHost.FilterYearsRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(ExploreNavigationHost)
            }

            val filterViewModel: FilterViewModel = hiltViewModel(parentEntry)
            val yearToSelect by filterViewModel.filteredYears.collectAsStateWithLifecycle()
            val filterFromYear by filterViewModel.filterFromYear.collectAsStateWithLifecycle()
            val filterToYear by filterViewModel.filterToYear.collectAsStateWithLifecycle()

            val yearRangeMap = mapOf(
                "fromYear" to filterFromYear,
                "toYear" to filterToYear,
            )

            FilterYearsScreen(
                turnBack = turnBack,
                resetFilters = { filterViewModel.resetFilters() },
                usedYearsFilter = yearToSelect,
                usedYearRangeFilter = yearRangeMap,
                acceptNewYearsFilter = { year, yearRange ->

                    filterViewModel.setFilteredYears(year)
                    filterViewModel.setFilteredYearsRange(
                        fromYear = yearRange["fromYear"].toString().toInt(),
                        toYear = yearRange["toYear"].toString().toInt()
                    )
                },
            )
        }

    }
}
