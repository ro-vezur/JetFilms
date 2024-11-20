package com.example.jetfilms.Helpers.encodes

import java.util.Base64

fun decodeStringWithSpecialCharacter(string: String): String {
    return String(Base64.getUrlDecoder().decode(string))
}