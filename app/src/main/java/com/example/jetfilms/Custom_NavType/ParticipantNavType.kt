package com.example.jetfilms.Custom_NavType

import android.os.Bundle
import androidx.navigation.NavType
import com.example.jetfilms.Data_Classes.ParticipantPackage.DetailedParticipantResponse
import kotlinx.serialization.json.Json

class ParticipantNavType : NavType<DetailedParticipantResponse>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): DetailedParticipantResponse? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): DetailedParticipantResponse {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: DetailedParticipantResponse) {
        bundle.putParcelable(key, value)
    }
}