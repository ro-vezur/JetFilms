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
            title = encodeStringWithSpecialCharacter(selectedSerial.title),
            poster = encodeStringWithSpecialCharacter(selectedSerial.poster.toString()),
        )
    )

    if(navController.currentDestination?.route.toString() != "serial_details/{serial}") {
        navController.navigate("serial_details/$jsonSerial")
    }
}