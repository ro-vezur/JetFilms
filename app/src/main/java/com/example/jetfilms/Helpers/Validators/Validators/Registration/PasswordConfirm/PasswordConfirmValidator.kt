package com.example.jetfilms.Helpers.Validators.Validators.Registration.PasswordConfirm

import com.example.jetfilms.Helpers.Validators.Results.ValidationResult

class PasswordConfirmValidator {
    operator fun invoke(password: String,passwordConfirm: String): ValidationResult {
        return when {
            passwordConfirm.isBlank() -> ValidationResult.ERROR
            passwordConfirm != password -> ValidationResult.ERROR
            else -> ValidationResult.CORRECT
        }
    }
}