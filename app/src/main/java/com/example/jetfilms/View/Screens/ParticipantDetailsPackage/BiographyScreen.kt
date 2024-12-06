package com.example.jetfilms.View.Screens.ParticipantDetailsPackage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.jetfilms.View.Components.Text.ExpandableText
import com.example.jetfilms.Models.DTOs.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.blueVerticalGradient
import com.example.jetfilms.Helpers.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.BASE_IMAGE_API_URL
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp
import com.example.jetfilms.ui.theme.typography

@Composable
fun BiographyScreen(participantDisplay: DetailedParticipantDisplay) {
    val typography = typography()
    val participantResponse = participantDisplay.participantResponse

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(16.sdp))

        ExpandableText(
                text = decodeStringWithSpecialCharacter(participantResponse.biography),
                minimizedMaxLines = 8,
                textStyle = TextStyle(
                    color = Color.LightGray.copy(0.9f),
                    fontWeight = FontWeight.W400,
                    fontSize = 13.ssp,
                    lineHeight = 18.ssp
                ),
                seeMoreStyle = TextStyle(
                    brush = blueVerticalGradient,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.ssp
                ),
                modifier = Modifier
                    .padding(horizontal = 14.sdp)
        )


        if(participantDisplay.images.profiles.isNotEmpty()) {

            Column(
                    verticalArrangement = Arrangement.spacedBy(9.sdp),
                    modifier = Modifier
                        .padding(start = 1.sdp,top = 16.sdp)
            ) {
                    Text(
                        text = "Photos",
                        style = typography.bodyMedium,
                        modifier = Modifier
                            .padding(start = 12.sdp)
                    )
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.sdp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        item { }

                        items(participantDisplay.images.profiles.take(6)) { image ->
                            AsyncImage(
                                model = BASE_IMAGE_API_URL + image.image,
                                contentDescription = "participant image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(105.sdp)
                                    .height(156.sdp)
                                    .clip(RoundedCornerShape(8.sdp))
                            )
                        }

                        item { }
                    }
            }
        }
    }
}
