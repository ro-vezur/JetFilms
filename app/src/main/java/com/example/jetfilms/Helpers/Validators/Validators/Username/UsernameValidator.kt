package com.example.jetfilms.Helpers.Validators.Validators.Username

import com.example.jetfilms.Helpers.Validators.Results.UsernameValidationResult

class UsernameValidator {
    operator fun invoke(username: String): UsernameValidationResult {
        return if (username.isBlank()) UsernameValidationResult.IS_EMPTY
        else UsernameValidationResult.CORRECT
    }
}