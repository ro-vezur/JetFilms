package com.example.jetfilms.Helpers.Validators.Validators.AccountScreen.ContactForm

import com.example.jetfilms.Helpers.Validators.Results.ValidationResult

class FeedbackValidator {

    operator fun invoke(feedback: String,maxLength: Int): ValidationResult {
        return when {
            feedback.isBlank() -> ValidationResult.ERROR
            feedback.length > maxLength -> ValidationResult.ERROR
            else -> ValidationResult.CORRECT
        }
    }
}