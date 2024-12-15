package com.example.jetfilms.Helpers.Validators.Validators.ContactForm

import com.example.jetfilms.Helpers.Validators.Results.FeedbackValidationResult
import com.example.jetfilms.TEXT_FIELD_MAX_LENGTH

class FeedbackValidator {

    operator fun invoke(feedback: String,maxLength: Int): FeedbackValidationResult {
        return when {
            feedback.isBlank() -> FeedbackValidationResult.ERROR
            feedback.length > maxLength -> FeedbackValidationResult.ERROR
            else -> FeedbackValidationResult.CORRECT
        }
    }
}