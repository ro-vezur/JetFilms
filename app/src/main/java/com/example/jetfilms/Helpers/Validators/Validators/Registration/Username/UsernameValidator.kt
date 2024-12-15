package com.example.jetfilms.Helpers.Validators.Validators.Registration.Username

import com.example.jetfilms.Helpers.Validators.Results.UsernameValidationResult

class UsernameValidator {
    operator fun invoke(username: String): UsernameValidationResult {
        return when {
            username.isBlank() -> UsernameValidationResult.ERROR
            username.length < 3 -> UsernameValidationResult.ERROR
            username.length > 20 -> UsernameValidationResult.ERROR
            else -> UsernameValidationResult.CORRECT
        }
    }
}