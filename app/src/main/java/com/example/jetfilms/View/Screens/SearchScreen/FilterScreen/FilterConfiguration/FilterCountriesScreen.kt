package com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterConfiguration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.View.Components.Buttons.AcceptMultipleSelectionButton
import com.example.jetfilms.View.Components.Gradient.animatedGradient
import com.example.jetfilms.View.Components.InputFields.SearchField
import com.example.jetfilms.Models.Enums.AnimatedGradientTypes
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.Helpers.Countries.getCountryList
import com.example.jetfilms.Helpers.Countries.getCountryName
import com.example.jetfilms.View.Components.TopBars.FiltersTopBar
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.View.states.rememberForeverLazyListState
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor
import java.util.Locale


@Composable
fun FilterCountriesScreen(
    turnBack: () -> Unit,
    resetFilters: () -> Unit,
    usedCountries: List<String>,
    acceptNewCountries: (countries:List<String>) -> Unit,
) {
    val typography = typography()

    val itemsSpacing = 10

    val selectedCountries = remember{ mutableStateListOf<String>() }
    val listState = rememberForeverLazyListState(key = "country filter")

    var searchText by remember{ mutableStateOf("") }
    var searchedCountries by remember { mutableStateOf<List<String>>(listOf()) }

    LaunchedEffect(searchText) {
        searchedCountries = getCountryList().filter { getCountryName(it).contains(searchText.uppercase(), true) }
    }

    LaunchedEffect(null) {
        selectedCountries.clear()
        selectedCountries.addAll(usedCountries)
    }

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            FiltersTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FILTER_TOP_BAR_HEIGHT.sdp),
                turnBack = {turnBack() },
                reset = resetFilters,
                text = "Countries"
            )
        }
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .padding(top = FILTER_TOP_BAR_HEIGHT.sdp)
                        .fillMaxWidth()
                        .height(42.sdp)
                ) {
                    SearchField(
                        fillWidth = 1f,
                        shape = RoundedCornerShape(bottomStart = 6.sdp, bottomEnd = 6.sdp),
                        text = searchText,
                        onTextChange = {
                            searchText = it
                        },
                        focusedBorder = null,
                        modifier = Modifier
                    )
                }

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(itemsSpacing.sdp),
                    state = listState,
                    modifier = Modifier
                ) {

                    item { Spacer(modifier = Modifier.height((0).sdp)) }

                    item {
                        val selected = selectedCountries.toList().sorted() == getCountryList()

                        CountryCard(
                            name = "All",
                            selected = selectedCountries.toList().sorted() == getCountryList(),
                            select = {
                                if (selected) {
                                    selectedCountries.clear()
                                } else {
                                    getCountryList().forEach { country ->
                                        if (!selectedCountries.contains(country)) {
                                            selectedCountries.add(country)
                                        }
                                    }
                                }
                            }
                        )
                    }

                    items(searchedCountries) { country ->

                        val selected =
                            selectedCountries.contains(country) && selectedCountries.toList()
                                .sorted() != getCountryList()

                        CountryCard(
                            name = country,
                            selected = selected,
                            select = {
                                if (selectedCountries.contains(country)) selectedCountries.remove(
                                    country
                                )
                                else selectedCountries.add(country)
                            }
                        )
                    }

                    item { Spacer(modifier = Modifier.height((BOTTOM_NAVIGATION_BAR_HEIGHT + BASE_BUTTON_HEIGHT + itemsSpacing).sdp)) }
                }
            }

            AcceptMultipleSelectionButton(
                isEmpty = selectedCountries.isEmpty(),
                isDataSameAsBefore = selectedCountries.toList().sorted() == usedCountries,
                onClick = {
                    turnBack()
                    acceptNewCountries(selectedCountries)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 9).sdp)
            )
        }
    }
}

@Composable
private fun CountryCard(
    name: String,
    selected: Boolean,
    select: () -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.92f)
            .height(50.sdp)
            .clip(RoundedCornerShape(8.sdp))
            .clickable { select() }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 4.sdp)
                .fillMaxWidth()
                .height(22.sdp)
        ) {
            Text(
                text = Locale("",name).displayName,
                fontSize = 15.ssp,
                fontWeight = FontWeight.W400,
                style = TextStyle(
                    brush = 
                    if(selected) animatedGradient(colors = listOf(buttonsColor1, buttonsColor2),
                        AnimatedGradientTypes.VERTICAL)
                    else animatedGradient(colors = listOf(whiteColor, whiteColor))
                ),
                modifier = Modifier
            )

            if(selected){
                Box(
                    modifier = Modifier
                        .size(21.sdp)
                        .clip(CircleShape)
                        .background(Brush.horizontalGradient(listOf(buttonsColor1, buttonsColor2)))

                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "check",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(16.sdp)
                    )
                }
            }
        }

        Divider(
            color = Color.DarkGray.copy(0.5f),
            thickness = 1.sdp,
            modifier = Modifier
                .padding(top = 15.sdp)
                .fillMaxWidth()
                .clip(CircleShape)
        )
    }
}