package com.example.jetfilms.View.Screens.SearchScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.View.Screens.ExploreScreen
import com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterConfiguration.filterNavGraph
import com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterResults.FilteredResultsScreen
import com.example.jetfilms.View.Screens.SearchScreen.Search.SearchScreen
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.FilterViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel
import com.example.jetfilms.ViewModels.SharedFilterConfigurationViewModel

fun NavGraphBuilder.exploreScreen(
    screensNavController: NavController,
    showBottomBar: (show: Boolean) -> Unit,
    selectMedia: (id: Int, type: MediaFormats) -> Unit,
    seeAllMedia: (type: MediaFormats, query: String) -> Unit,
    filterViewModel: FilterViewModel,
    searchHistoryViewModel: SearchHistoryViewModel,
    sharedFilterConfigurationViewModel: SharedFilterConfigurationViewModel,
) {
    navigation<ExploreScreen>(
        startDestination = ExploreScreen.SearchScreen,
    ) {
        composable<ExploreScreen.SearchScreen> {
            val filteredResults = filterViewModel.filteredResults.collectAsLazyPagingItems()

            showBottomBar(true)

            SearchScreen(
                selectMedia = selectMedia,
                seeAllMedia = seeAllMedia,
                onFilterButtonClick = {
                    if (filteredResults.itemCount != 0) {
                        screensNavController.navigate(ExploreScreen.FilteredResults)
                    } else {
                        screensNavController.navigate(ExploreScreen.FilterConfiguration)
                    }
                },
                searchHistoryViewModel =searchHistoryViewModel
            )
        }

        composable<ExploreScreen.FilteredResults> {
            val filteredResults = filterViewModel.filteredResults.collectAsLazyPagingItems()

            showBottomBar(true)

            FilteredResultsScreen(
                filteredResults = filteredResults,
                turnBack = { screensNavController.navigate(ExploreScreen.SearchScreen) },
                reset = {
                    sharedFilterConfigurationViewModel.setSortType(null)
                    sharedFilterConfigurationViewModel.setGenres(BASE_MEDIA_GENRES)
                    sharedFilterConfigurationViewModel.setCategories(MediaFormats.entries.toList())
                    sharedFilterConfigurationViewModel.setCountries(getCountryList())

                    filterViewModel.setSelectedSort(null)
                    filterViewModel.setFilteredGenres(BASE_MEDIA_GENRES)
                    filterViewModel.setFilteredCategories(MediaFormats.entries.toList())
                    filterViewModel.setFilteredCountries(getCountryList())

                    screensNavController.navigate(ExploreScreen.FilterConfiguration)
                },
                selectMedia = selectMedia,
                onFilterButtonClick = {
                    screensNavController.navigate(ExploreScreen.FilterConfiguration)
                }
            )
        }

        filterNavGraph(
            navController = screensNavController,
            filterViewModel = filterViewModel,
         //   sharedFilterConfigurationViewModel = sharedFilterConfigurationViewModel
        )
    }
}