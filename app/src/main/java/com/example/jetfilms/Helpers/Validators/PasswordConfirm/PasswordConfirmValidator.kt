package com.example.jetfilms.Helpers.Validators.PasswordConfirm

class PasswordConfirmValidator {
    operator fun invoke(password: String,passwordConfirm: String): PasswordConfirmValidationResult {
        return if (passwordConfirm.isBlank()) PasswordConfirmValidationResult.EMPTY_FIELD
        else if (passwordConfirm != password) PasswordConfirmValidationResult.NOT_MATCH
        else PasswordConfirmValidationResult.CORRECT
    }
}