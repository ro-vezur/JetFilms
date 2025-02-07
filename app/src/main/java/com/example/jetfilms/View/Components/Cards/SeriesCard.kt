package com.example.jetfilms.View.Components.Cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.jetfilms.Models.DTOs.SeriesPackage.SimplifiedSeriesResponse
import com.example.jetfilms.Helpers.removeNumbersAfterDecimal
import com.example.jetfilms.R
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.View.Components.OptimizedImage
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

@Composable
fun SeriesCard(
    modifier: Modifier = Modifier,
    serial: SimplifiedSeriesResponse,
) {
    Box(
        modifier = modifier
    ){
        OptimizedImage(
            url = "$BASE_IMAGE_API_URL${serial.poster}",
            modifier = modifier
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.sdp),
            modifier = Modifier
                .padding(top = 5.sdp, start = 3.sdp)
                .clip(RoundedCornerShape(3.sdp))
                .background(Color.LightGray.copy(0.28f))
        ){
            Image(
                painter = painterResource(id = R.drawable.imdb_logo_2016_svg),
                contentDescription = "IMDb",
                modifier = Modifier
                    .padding(start = 2.sdp)
                    .width(22.sdp)
                    .clip(RoundedCornerShape(3.sdp))
            )

            Text(
                text = removeNumbersAfterDecimal(serial.rating,2).toString(),
                fontSize = 13.ssp,
                color = Color.White,
                modifier = Modifier
                    .padding(end = 2.sdp)
            )
        }
    }
}