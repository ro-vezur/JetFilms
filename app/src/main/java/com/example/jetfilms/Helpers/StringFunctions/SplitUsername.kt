package com.example.jetfilms.Helpers.StringFunctions


// split username into first name and last name when user log in with google
fun splitUsername(fullName: String): Pair<String, String> {
    val regex = "\\s+".toRegex()
    val parts = fullName.split(regex)
    return if (parts.size > 1) {
        Pair(parts[0], parts.subList(1, parts.size).joinToString(" "))
    } else {
        Pair(parts[0], "")
    }
}