package com.example.jetfilms.Helpers.navigate

import androidx.navigation.NavController
import com.example.jetfilms.Models.DTOs.SeriesPackage.DetailedSeriesResponse
import com.example.jetfilms.Helpers.encodes.encodeStringWithSpecialCharacter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun navigateToSelectedSerial(navController: NavController, selectedSerial: DetailedSeriesResponse){

    val jsonSerial = Json.encodeToString(
        selectedSerial.copy(
            overview = encodeStringWithSpecialCharacter(selectedSerial.overview),
            name = encodeStringWithSpecialCharacter(selectedSerial.name.toString()),
            poster = encodeStringWithSpecialCharacter(selectedSerial.poster.toString()),
        )
    )

    navController.navigate("serial_details/$jsonSerial")
}