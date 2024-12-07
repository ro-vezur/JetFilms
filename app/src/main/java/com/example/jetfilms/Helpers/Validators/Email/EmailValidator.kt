package com.example.jetfilms.Helpers.Validators.Email

import android.util.Patterns

class EmailValidator {
    operator fun invoke(email: String): EmailValidationResult {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailValidationResult.CORRECT
        }
        else if(email.isBlank()) EmailValidationResult.EMPTY_FIELD
        else EmailValidationResult.INCORRECT_FORMAT
    }
}