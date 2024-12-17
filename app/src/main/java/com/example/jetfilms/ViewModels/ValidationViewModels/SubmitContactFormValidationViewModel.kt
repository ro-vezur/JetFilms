package com.example.jetfilms.ViewModels.ValidationViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Helpers.Validators.Results.ValidationResult
import com.example.jetfilms.Helpers.Validators.Validators.AccountScreen.ContactForm.FeedbackValidator
import com.example.jetfilms.Helpers.Validators.Validators.Registration.Username.UsernameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubmitContactFormValidationViewModel @Inject constructor(): ViewModel() {

    private val _usernameValidation: MutableStateFlow<ValidationResult> = MutableStateFlow(
        ValidationResult.NONE
    )
    val usernameValidation: StateFlow<ValidationResult> = _usernameValidation.asStateFlow()

    private val _feedBackValidation: MutableStateFlow<ValidationResult> = MutableStateFlow(
        ValidationResult.NONE
    )
    val feedBackValidation: StateFlow<ValidationResult> = _feedBackValidation.asStateFlow()

    fun validation(
        name: String,
        feedback: String,
        maxFeedbackLength: Int,
    ): Boolean {
        val usernameValidator = UsernameValidator().invoke(
            username = name
        )
        val feedbackValidator = FeedbackValidator().invoke(
            feedback = feedback,
            maxLength = maxFeedbackLength,
        )

        setUsernameValidationResult(usernameValidator)
        setFeedbackValidationResult(feedbackValidator)

        return usernameValidator == ValidationResult.CORRECT &&
                feedbackValidator == ValidationResult.CORRECT
    }

    fun setUsernameValidationResult(result: ValidationResult) = viewModelScope.launch {
        _usernameValidation.emit(result)
    }

    fun setFeedbackValidationResult(result: ValidationResult) = viewModelScope.launch {
        _feedBackValidation.emit(result)
    }
}