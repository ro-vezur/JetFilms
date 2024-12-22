package com.example.jetfilms.View.Screens.SearchScreen.FilterScreen.FilterConfiguration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.jetfilms.BASE_BUTTON_WIDTH
import com.example.jetfilms.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetfilms.FILTER_TOP_BAR_HEIGHT
import com.example.jetfilms.Helpers.DateFormats.DateFormats
import com.example.jetfilms.View.Components.Buttons.AcceptMultipleSelectionButton
import com.example.jetfilms.View.Components.InputFields.TextInPutField.TextInputField
import com.example.jetfilms.View.Components.TabRow
import com.example.jetfilms.View.Components.TopBars.FiltersTopBar
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.primaryColor
import com.example.jetfilms.ui.theme.typography
import com.example.jetfilms.ui.theme.whiteColor

@Composable
fun FilterYearsScreen(
    turnBack: () -> Unit,
    resetFilters: () -> Unit,
    usedYearsFilter: Int,
    usedYearRangeFilter: Map<String,String>,
    acceptNewYearsFilter: (year: Int, yearRange: Map<String,String>) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = {2})

    var yearsFilterToSet by remember { mutableStateOf(usedYearsFilter.toString()) }

    val usedFromYear = DateFormats.getYear(usedYearRangeFilter["fromYear"].toString()).toString()
    val usedToYear = DateFormats.getYear(usedYearRangeFilter["toYear"].toString()).toString()

    var filterFromYearToSet by remember{ mutableStateOf(usedFromYear) }
    var filterToYearToSet by remember{ mutableStateOf(usedToYear) }

    Scaffold(
        containerColor = primaryColor,
        topBar = {
            FiltersTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FILTER_TOP_BAR_HEIGHT.sdp),
                turnBack = {turnBack() },
                reset = resetFilters,
                text = "Years",
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                TabRow(
                    tabs = listOf("Specific Year", "Year Range"),
                    pagerState = pagerState
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .padding(top = 15.sdp),
                    userScrollEnabled = false
                ) { page ->
                    when (page) {
                        0 -> {
                            EnterSpecificYearScreen(
                                text = yearsFilterToSet,
                                onTextChange = { yearsFilterToSet = it}
                            )
                        }

                        1 -> {
                            EnterYearRangeScreen(
                                textFromYear = filterFromYearToSet,
                                textToYear = filterToYearToSet,
                                onChangeTextFromYear = { filterFromYearToSet = it },
                                onChangeTextToYear = { filterToYearToSet = it }
                            )
                        }
                    }
                }
            }



            AcceptMultipleSelectionButton(
                isEmpty = if(pagerState.currentPage == 0) yearsFilterToSet.isBlank()
                else filterFromYearToSet.isBlank() || filterToYearToSet.isBlank(),
                isDataSameAsBefore = if(pagerState.currentPage == 0) yearsFilterToSet == usedYearsFilter.toString()
                else filterFromYearToSet == usedFromYear && filterToYearToSet == usedToYear,
                additionalText = "Enter a Year",
                onClick = {

                    turnBack()
                    acceptNewYearsFilter(
                        if(pagerState.currentPage == 0) yearsFilterToSet.toInt() else 0,
                        mapOf(
                            "fromYear" to if(pagerState.currentPage == 1) filterFromYearToSet else "0",
                            "toYear" to if(pagerState.currentPage == 1) filterToYearToSet else "0"
                        )
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = (BOTTOM_NAVIGATION_BAR_HEIGHT + 9).sdp)
            )
        }
    }
}

@Composable
private fun EnterSpecificYearScreen(
    text: String,
    onTextChange: (value: String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Enter Specific Year",
            style = typography().headlineSmall,
            color = whiteColor,
            modifier = Modifier
                .padding(top = 15.sdp)
        )

        TextInputField(
            height = 45.sdp,
            width = BASE_BUTTON_WIDTH.sdp,
            modifier = Modifier
                .padding(top = 28.sdp),
            text = text,
            textStyle = typography().bodyMedium,
            onTextChange = onTextChange,
            placeHolder = "Year",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            )
        )
    }
}

@Composable
private fun EnterYearRangeScreen(
    textFromYear: String,
    textToYear: String,
    onChangeTextFromYear: (value: String) -> Unit,
    onChangeTextToYear: (value: String) -> Unit,
) {
    val fieldsWidth = 135.sdp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Enter Year Range",
            style = typography().headlineSmall,
            color = whiteColor,
            modifier = Modifier
                .padding(top = 15.sdp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(top = 28.sdp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextInputField(
                    height = 45.sdp,
                    width = fieldsWidth,
                    modifier = Modifier,
                    text = textFromYear,
                    textStyle = typography().bodyMedium,
                    onTextChange = onChangeTextFromYear,
                    placeHolder = "From Year",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    )
                )

                Text(
                    text = "From",
                    style = typography().headlineSmall,
                    color = whiteColor,
                    modifier = Modifier
                        .padding(top = 10.sdp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextInputField(
                    height = 45.sdp,
                    width = fieldsWidth,
                    modifier = Modifier,
                    text = textToYear,
                    textStyle = typography().bodyMedium,
                    onTextChange = onChangeTextToYear,
                    placeHolder = "To Year",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    )
                )

                Text(
                    text = "To",
                    style = typography().headlineSmall,
                    color = whiteColor,
                    modifier = Modifier
                        .padding(top = 10.sdp)
                )
            }
        }
    }
}