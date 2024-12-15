package com.example.jetfilms.Helpers.Validators.Validators.Registration.Password

import com.example.jetfilms.Helpers.Validators.Results.PasswordValidationResult

class PasswordValidator {
    operator fun invoke(password: String, checkIfPasswordNotMatches: Boolean): PasswordValidationResult {
        return when {
            password.length < 8 -> PasswordValidationResult.ERROR
            password.count(Char::isUpperCase) == 0 -> PasswordValidationResult.ERROR
            checkIfPasswordNotMatches -> PasswordValidationResult.ERROR
            else -> PasswordValidationResult.CORRECT
        }
    }
}