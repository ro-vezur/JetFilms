package com.example.jetfilms.Helpers.navigate

import androidx.navigation.NavController
import com.example.jetfilms.DTOs.SeriesPackage.DetailedSerialResponse
import com.example.jetfilms.Helpers.encodes.encodeStringWithSpecialCharacter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun navigateToSelectedSerial(navController: NavController, selectedSerial: DetailedSerialResponse){

    val jsonSerial = Json.encodeToString(
        selectedSerial.copy(
            overview = encodeStringWithSpecialCharacter(selectedSerial.overview),
            name = encodeStringWithSpecialCharacter(selectedSerial.name),
            poster = encodeStringWithSpecialCharacter(selectedSerial.poster.toString()),
        )
    )

    navController.navigate("serial_details/$jsonSerial")
}