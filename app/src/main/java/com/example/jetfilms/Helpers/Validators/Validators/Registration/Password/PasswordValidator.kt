package com.example.jetfilms.Helpers.Validators.Validators.Registration.Password

import com.example.jetfilms.Helpers.Validators.Results.ValidationResult

class PasswordValidator {
    operator fun invoke(password: String, checkIfPasswordNotMatches: Boolean): ValidationResult {
        return when {
            password.length < 8 -> ValidationResult.ERROR
            password.count(Char::isUpperCase) == 0 -> ValidationResult.ERROR
            checkIfPasswordNotMatches -> ValidationResult.ERROR
            else -> ValidationResult.CORRECT
        }
    }
}