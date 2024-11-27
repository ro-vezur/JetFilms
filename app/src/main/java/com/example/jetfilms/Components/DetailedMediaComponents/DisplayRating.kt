package com.example.jetfilms.Components.DetailedMediaComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.jetfilms.Helpers.removeNumbersAfterDecimal
import com.example.jetfilms.R
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

@Composable
fun DisplayRating(rating: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.sdp),
        modifier = Modifier.padding(bottom = 6.sdp)
    ){
        Image(
            painter = painterResource(id = R.drawable.imdb_logo_2016_svg),
            contentDescription = "IMDb",
            modifier = Modifier
                .width(30.sdp)
                .clip(RoundedCornerShape(3.sdp))
        )

        Text(
            text = removeNumbersAfterDecimal(rating,2).toString(),
            fontSize = 21f.ssp
        )
    }
}