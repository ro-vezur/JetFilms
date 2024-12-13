package com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterConfiguration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.View.Screens.ExploreScreen
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.FilterViewModel
import com.example.jetfilms.extensions.popBackStackOrIgnore


fun NavGraphBuilder.filterNavGraph(
    navController: NavController,
    filterViewModel: FilterViewModel,
   // ssharedFilterConfigurationViewModel: SharedFilterConfigurationViewModel,
) {
    navigation<ExploreScreen.FilterConfiguration>(
        startDestination = ExploreScreen.FilterConfiguration.AcceptFilters
    ){
        val turnBack = {
            navController.popBackStackOrIgnore()
        }

        fun resetFilter() {
            filterViewModel.setSelectedSort(null)
            filterViewModel.setFilteredGenres(BASE_MEDIA_GENRES)
            filterViewModel.setFilteredCategories(MediaFormats.entries.toList())
            filterViewModel.setFilteredCountries(getCountryList())

            filterViewModel.setSelectedSort(null)
            filterViewModel.setFilteredGenres(BASE_MEDIA_GENRES)
            filterViewModel.setFilteredCategories(MediaFormats.entries.toList())
            filterViewModel.setFilteredCountries(getCountryList())
        }

        composable<ExploreScreen.FilterConfiguration.AcceptFilters> {
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
                resetFilters = { resetFilter() },
                selectSortType = { newSortType -> filterViewModel.setSelectedSort(newSortType)},
                navController = navController,
                selectedSortType = selectedSort,
                genresToSelect = genresToSelect,
                categoriesToSelect = categoriesToSelect,
                countriesToSelect = countriesToSelect,
                yearsFilterToSelect = yearFilterToSet,
                yearsFilterRange = mapOf(
                    "fromYear" to yearFilterFrom,
                    "toYear" to yearFilterTo
                ),
            ) {
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
                    year = yearFilterToSet,
                    yearRange = mapOf(
                        "fromYear" to yearFilterFrom,
                        "toYear" to yearFilterTo
                    )
                )
            }
        }

        composable<ExploreScreen.FilterConfiguration.FilterGenres> {
            val genresToSelect by filterViewModel.genresFilter.collectAsStateWithLifecycle()

            FilterGenresScreen(
                turnBack = turnBack,
                resetFilters = { resetFilter() },
                usedGenres = genresToSelect,
                acceptNewGenres = { genres ->
                    filterViewModel.setFilteredGenres(genres)
                },
            )
        }

        composable<ExploreScreen.FilterConfiguration.FilterCategories> {
            val categoriesToSelect by filterViewModel.categoriesFilter.collectAsStateWithLifecycle()

            FilterCategoriesScreen(
                turnBack = turnBack,
                resetFilters = { resetFilter() },
                usedCategories = categoriesToSelect,
                acceptNewCategories = { categories ->
                    filterViewModel.setFilteredCategories(categories)
                }
            )
        }

        composable<ExploreScreen.FilterConfiguration.FilterCountries> {
            val countriesToSelect by filterViewModel.filteredCountries.collectAsStateWithLifecycle()

            FilterCountriesScreen(
                turnBack = turnBack,
                resetFilters = {resetFilter()},
                usedCountries = countriesToSelect,
                acceptNewCountries = { countries ->
                    filterViewModel.setFilteredCountries(countries)
                }
            )
        }

        composable<ExploreScreen.FilterConfiguration.FilterYears> {
            val yearToSelect by filterViewModel.filteredYears.collectAsStateWithLifecycle()
            val filterFromYear by filterViewModel.filterFromYear.collectAsStateWithLifecycle()
            val filterToYear by filterViewModel.filterToYear.collectAsStateWithLifecycle()

            val yearRangeMap = mapOf(
                "fromYear" to filterFromYear,
                "toYear" to filterToYear,
            )

            FilterYearsScreen(
                turnBack = turnBack,
                resetFilters = { resetFilter() },
                usedYearsFilter = yearToSelect,
                usedYearRangeFilter = yearRangeMap,
                acceptNewYearsFilter = { year, yearRange ->
                    Log.d("year range",yearRange.toString())

                    Log.d("fromYear",yearRange["fromYear"].toString())

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
/*
val yearRangeMap = mapOf(
                "fromYear" to filterFromYear,
                "toYear" to filterToYear,
            )
 */