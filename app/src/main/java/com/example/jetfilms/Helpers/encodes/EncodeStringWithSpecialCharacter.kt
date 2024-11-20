package com.example.jetfilms.Helpers.encodes

import java.util.Base64

fun encodeStringWithSpecialCharacter(string: String): String{
    return Base64.getUrlEncoder().encodeToString(string.toByteArray())
}
