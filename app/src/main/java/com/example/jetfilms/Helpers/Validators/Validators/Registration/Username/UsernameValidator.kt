package com.example.jetfilms.Helpers.Validators.Validators.Registration.Username

import com.example.jetfilms.Helpers.Validators.Results.ValidationResult

class UsernameValidator {
    operator fun invoke(username: String): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult.ERROR
            username.length < 3 -> ValidationResult.ERROR
            username.length > 20 -> ValidationResult.ERROR
            else -> ValidationResult.CORRECT
        }
    }
}