package com.example.jetfilms.Helpers.Validators.Validators.Registration.PasswordConfirm

import com.example.jetfilms.Helpers.Validators.Results.PasswordConfirmValidationResult

class PasswordConfirmValidator {
    operator fun invoke(password: String,passwordConfirm: String): PasswordConfirmValidationResult {
        return when {
            passwordConfirm.isBlank() -> PasswordConfirmValidationResult.ERROR
            passwordConfirm != password -> PasswordConfirmValidationResult.ERROR
            else -> PasswordConfirmValidationResult.CORRECT
        }
    }
}