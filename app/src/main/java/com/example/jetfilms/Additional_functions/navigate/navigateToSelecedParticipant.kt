package com.example.jetfilms.Additional_functions.navigate

import androidx.navigation.NavController
import com.example.jetfilms.Data_Classes.ParticipantPackage.DetailedParticipantResponse
import com.example.jetfilms.encodes.encodeStringWithSpecialCharacter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun navigateToSelectedParticipant(navController: NavController,selectedParticipantResponse: DetailedParticipantResponse){
    val jsonParticipant = Json.encodeToString(
        selectedParticipantResponse.copy(
            name = encodeStringWithSpecialCharacter(selectedParticipantResponse.name),
            activity = encodeStringWithSpecialCharacter(selectedParticipantResponse.activity),
            biography = encodeStringWithSpecialCharacter(selectedParticipantResponse.biography),
            image = encodeStringWithSpecialCharacter(selectedParticipantResponse.image.toString()),
        )
    )

    navController.navigate("participant_details/$jsonParticipant")
}