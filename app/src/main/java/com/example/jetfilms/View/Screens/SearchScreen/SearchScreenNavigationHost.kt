package com.example.jetfilms.View.Screens.SearchScreen

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetfilms.View.Screens.ExploreNavigationHost
import com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterConfiguration.filterNavGraph
import com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterResults.FilteredResultsScreen
import com.example.jetfilms.View.Screens.SearchScreen.Search.SearchScreen
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.example.jetfilms.ViewModels.FilterViewModel
import com.example.jetfilms.ViewModels.SearchHistoryViewModel

fun NavGraphBuilder.exploreScreen(
    navController: NavController,
    showBottomBar: (show: Boolean) -> Unit,
    selectMedia: (id: Int, type: MediaCategories) -> Unit,
    seeAllMedia: (type: MediaCategories, query: String) -> Unit,
) {
    navigation<ExploreNavigationHost>(
        startDestination = ExploreNavigationHost.SearchRoute,
    ) {
        composable<ExploreNavigationHost.SearchRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(ExploreNavigationHost)
            }

            val filterViewModel: FilterViewModel = hiltViewModel(parentEntry)
            val filteredResults = filterViewModel.filteredResults.collectAsLazyPagingItems()

            val searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel()

            showBottomBar(true)

            SearchScreen(
                selectMedia = selectMedia,
                seeAllMedia = seeAllMedia,
                onFilterButtonClick = {
                    if (filteredResults.itemCount != 0) {
                        navController.navigate(ExploreNavigationHost.FilteredResultsRoute)
                    } else {
                        navController.navigate(ExploreNavigationHost.FilterConfigurationNavigationHost)
                    }
                },
                searchHistoryViewModel = searchHistoryViewModel
            )
        }

        composable<ExploreNavigationHost.FilteredResultsRoute> { backstackEntry ->
            val parentEntry = remember(backstackEntry) {
                navController.getBackStackEntry(ExploreNavigationHost)
            }

            val filterViewModel: FilterViewModel = hiltViewModel(parentEntry)
            val filteredResults = filterViewModel.filteredResults.collectAsLazyPagingItems()

            showBottomBar(true)

            FilteredResultsScreen(
                filteredResults = filteredResults,
                turnBack = { navController.navigate(ExploreNavigationHost.SearchRoute) },
                reset = {
                    filterViewModel.resetFilters()
                    navController.navigate(ExploreNavigationHost.FilterConfigurationNavigationHost)
                },
                selectMedia = selectMedia,
                onFilterButtonClick = {
                    navController.navigate(ExploreNavigationHost.FilterConfigurationNavigationHost)
                }
            )
        }

        filterNavGraph(
            navController = navController,
        )
    }
}