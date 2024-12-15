package com.example.jetfilms.Helpers.Validators.Validators.Registration.Email

import android.util.Patterns
import com.example.jetfilms.Helpers.Validators.Results.EmailValidationResult

class EmailValidator {
    operator fun invoke(email: String, checkIfEmailAlreadyRegistered: Boolean): EmailValidationResult {
        return when {
            email.isBlank() -> EmailValidationResult.ERROR
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> EmailValidationResult.ERROR
            checkIfEmailAlreadyRegistered -> EmailValidationResult.ERROR
            else -> EmailValidationResult.CORRECT
        }
    }
}