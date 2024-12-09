package com.example.jetfilms.Helpers.Validators.Validators.PasswordConfirm

import com.example.jetfilms.Helpers.Validators.Results.PasswordConfirmValidationResult

class PasswordConfirmValidator {
    operator fun invoke(password: String,passwordConfirm: String): PasswordConfirmValidationResult {
        return if (passwordConfirm.isBlank()) PasswordConfirmValidationResult.EMPTY_FIELD
        else if (passwordConfirm != password) PasswordConfirmValidationResult.NOT_MATCH
        else PasswordConfirmValidationResult.CORRECT
    }
}