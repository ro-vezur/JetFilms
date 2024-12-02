package com.example.jetfilms.Components.TabsContent

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetfilms.DTOs.MoviePackage.MovieDisplay
import com.example.jetfilms.DTOs.MoviePackage.SimplifiedMovieDataClass
import com.example.jetfilms.DTOs.UnifiedDataPackage.SimplifiedParticipantResponse
import com.example.jetfilms.Screens.MovieDetailsPackage.MovieAboutScreen
import com.example.jetfilms.Screens.MovieDetailsPackage.MovieMoreLikeThisScreen
import com.example.jetfilms.Screens.MovieDetailsPackage.MovieTrailersScreen

@Composable
fun MovieAboutTab(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    movieDisplay: MovieDisplay,
    selectMovie: (id: Int) -> Unit,
    navigateToSelectedParticipant: (participant: SimplifiedParticipantResponse) -> Unit
) {
    val movieResponse = movieDisplay.response

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = false
    ) { index ->
        when (index) {
            0 -> {
                MovieTrailersScreen(movie = movieResponse)
            }

            1 -> {
                MovieMoreLikeThisScreen(
                    similarMovies = movieDisplay.similarMovies,
                    selectMovie = selectMovie
                )
            }

            2 -> {
                MovieAboutScreen(
                    movieDisplay = movieDisplay,
                    navigateToSelectedParticipant = navigateToSelectedParticipant
                )
            }
        }
    }
}