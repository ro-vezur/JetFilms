package com.example.jetfilms.Helpers.Validators.Validators.Email

import android.util.Patterns
import com.example.jetfilms.Helpers.Validators.Results.EmailValidationResult

class EmailValidator {
    suspend operator fun invoke(email: String,additionalValidators: suspend () -> Boolean = {true}): EmailValidationResult {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && additionalValidators()) {
            EmailValidationResult.CORRECT
        }
        else if(email.isBlank()) EmailValidationResult.ERROR
        else EmailValidationResult.ERROR
    }
}