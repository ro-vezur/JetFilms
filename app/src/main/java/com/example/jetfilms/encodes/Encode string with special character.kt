package com.example.jetfilms.encodes

import java.util.Base64

fun encodeStringWithSpecialCharacter(string: String): String{
    return Base64.getUrlEncoder().encodeToString(string.toByteArray())
}

fun decodeStringWithSpecialCharacter(string: String): String {
    return String(Base64.getUrlDecoder().decode(string))
}