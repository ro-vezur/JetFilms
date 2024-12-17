package com.example.jetfilms.Helpers.Validators.Validators.Registration.Email

import android.util.Patterns
import com.example.jetfilms.Helpers.Validators.Results.ValidationResult

class EmailValidator {
    operator fun invoke(email: String, checkIfEmailAlreadyRegistered: Boolean): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.ERROR
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult.ERROR
            checkIfEmailAlreadyRegistered -> ValidationResult.ERROR
            else -> ValidationResult.CORRECT
        }
    }
}