package com.example.jetfilms.Screens.ParticipantDetailsPackage

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.jetfilms.CustomComposables.Text.ExpandableText
import com.example.jetfilms.Data_Classes.ParticipantPackage.DetailedParticipantDisplay
import com.example.jetfilms.blueVerticalGradient
import com.example.jetfilms.encodes.decodeStringWithSpecialCharacter
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.extensions.ssp

@Composable
fun BiographyScreen(participantDisplay: DetailedParticipantDisplay) {

    val participantResponse = participantDisplay.participantResponse

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
}