package com.example.jetfilms.Screens.DetailedMediaScreens.SerialDetailsPackage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetfilms.Components.Cards.TrailerCard
import com.example.jetfilms.DTOs.MoviePackage.MovieDisplay
import com.example.jetfilms.DTOs.SeriesPackage.SerialDisplay
import com.example.jetfilms.DTOs.TrailersResponse.TrailerObject
import com.example.jetfilms.Helpers.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.extensions.sdp

@Composable
fun SeriesTrailersScreen(
    seriesDisplay: SerialDisplay,
    selectTrailer: (trailer: TrailerObject) -> Unit,
) {
    Column {
        seriesDisplay.seriesTrailers.results.filter { it.type == "Trailer" && it.site == "YouTube" }.forEach { trailer ->
            TrailerCard(
                modifier = Modifier
                    .padding(horizontal = 6.sdp, vertical = 6.sdp)
                    .fillMaxWidth()
                    .height(105.sdp)
                    .clickable { selectTrailer(trailer) },
                trailerObject = trailer,
                selectedMediaName = decodeStringWithSpecialCharacter(seriesDisplay.response.name)
            )
        }
    }
}