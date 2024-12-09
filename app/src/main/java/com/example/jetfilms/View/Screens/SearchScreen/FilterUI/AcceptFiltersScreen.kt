package com.example.jetfilms.View.Screens.SearchScreen.FilterUI

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetfilms.BASE_MEDIA_GENRES
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.Cards.SortSelectedCard
import com.example.jetfilms.Models.DTOs.Filters.Filter
import com.example.jetfilms.Models.DTOs.Filters.SortTypes
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.Helpers.ListToString.StringListToString
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaFormats
import com.example.jetfilms.ViewModels.UnifiedMediaViewModel
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.View.states.rememberForeverScrollState
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor
import java.util.Locale

@Composable
fun AcceptFiltersScreen(
    turnBack: () -> Unit,
    navController: NavController,
    unifiedMediaViewModel: UnifiedMediaViewModel,
    genresToSelect: List<MediaGenres>,
    categoriesToSelect: List<MediaFormats>,
    countriesToSelect: List<String>,
    acceptNewFilters: (sortToSelect: SortTypes?) -> Unit,
    ) {
    val typography = typography()

    val selectedSort = unifiedMediaViewModel.selectedSort.collectAsStateWithLifecycle()

    var sortToSelect by remember { mutableStateOf(selectedSort.value) }

    val selectedGenresTextList =
        if (genresToSelect.sorted() == BASE_MEDIA_GENRES) "All"
        else {
            if (genresToSelect.isNotEmpty()) {
                genresToSelect.first().genre +
                        if(genresToSelect.size > 1) ",+${genresToSelect.size-1}"
                        else ""
            }
            else{
                ""
            }
        }

    val selectedCategoriesTextList = StringListToString(categoriesToSelect.map { it.format })

    val selectedCountriesTextList =
        if (countriesToSelect.sorted() == getCountryList()) "All"
        else {
            if (countriesToSelect.isNotEmpty()) {
                Locale("",countriesToSelect.first()).displayName +
                        if(countriesToSelect.size > 1) ",+${countriesToSelect.size-1}"
                        else ""
            }
            else{
                ""
            }
        }

    val filters = listOf(
        Filter("Categories",selectedCategoriesTextList),
        Filter("Genres",selectedGenresTextList),
        Filter("Country",selectedCountriesTextList),
        Filter("Year",""),
    )

    val scrollState = rememberForeverScrollState(key = "filter categories")
      
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 12.sdp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(11.sdp),
            modifier = Modifier
                .padding(top = (FILTER_TOP_BAR_HEIGHT + 15).sdp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Sorting",
                style = typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.sdp),
                modifier = Modifier
                    .padding(start = 0.sdp)
            ) {
                SortTypes.entries.forEach { type ->
                    SortSelectedCard(
                        text = type.title,
                        lengthMultiplayer = 9,
                        selected = sortToSelect == type,
                        onClick = { sortToSelect = type }
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(3.sdp),
            modifier = Modifier
                .padding(top = 22.sdp, bottom = 8.sdp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Filters",
                style = typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 3.sdp)
            )

            filters.forEach { filter ->
               FilterCard(
                   filter = filter,
                   onClick = {
                       navController.navigate(filter.name)
                   }
               )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = {
                val sortBy = sortToSelect?.requestQuery.toString()

                unifiedMediaViewModel.showFilteredData(true)
                unifiedMediaViewModel.setSelectedSort(sortToSelect)
                unifiedMediaViewModel.setFilteredGenres(genresToSelect)
                unifiedMediaViewModel.setFilteredCategories(categoriesToSelect)
                unifiedMediaViewModel.setFilteredCountries(countriesToSelect)

                acceptNewFilters(sortToSelect)

                turnBack() 
                      },
            textStyle = typography.bodyMedium.copy(color = whiteColor),
            gradient = blueHorizontalGradient,
            text = "Accept Filters",
            modifier = Modifier
                .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 9).sdp)
        )
    }
}


@Composable
private fun FilterCard(filter: Filter, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.sdp),
        modifier = Modifier
            .padding(top = 8.sdp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.sdp))
            .clickable { onClick() }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 5.sdp)
                .fillMaxWidth()
        ) {
            Text(
                text = filter.name,
                fontSize = 17.ssp,
                fontWeight = FontWeight.W400,
                color = Color.White,
                modifier = Modifier
            )

            Text(
                text = filter.value,
                fontSize = 14.ssp,
                fontWeight = FontWeight.W500,
                color = Color.Gray.copy(0.9f),
                modifier = Modifier
            )
        }

        Divider(
            color = Color.DarkGray.copy(0.5f),
            thickness = 2f.sdp,
            modifier = Modifier
                .padding(bottom = 6.sdp)
                .fillMaxWidth()
                .clip(CircleShape)
        )
    }
}