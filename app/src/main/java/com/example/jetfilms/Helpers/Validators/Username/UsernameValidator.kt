package com.example.jetfilms.Helpers.Validators.Username

class UsernameValidator {
    operator fun invoke(username: String): UsernameValidationResult {
        return if (username.isBlank()) UsernameValidationResult.IS_EMPTY
        else UsernameValidationResult.CORRECT
    }
}