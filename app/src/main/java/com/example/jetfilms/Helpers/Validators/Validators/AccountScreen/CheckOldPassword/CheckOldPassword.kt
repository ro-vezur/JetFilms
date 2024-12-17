package com.example.jetfilms.Helpers.Validators.Validators.AccountScreen.CheckOldPassword

import com.example.jetfilms.Helpers.Validators.Results.ValidationResult

class CheckOldPassword {
    operator fun invoke(oldPassword: String,enteredPassword: String): ValidationResult {
        return when {
            enteredPassword.isBlank() -> ValidationResult.ERROR
            oldPassword != enteredPassword -> ValidationResult.ERROR
            else -> ValidationResult.CORRECT
        }
    }
}
