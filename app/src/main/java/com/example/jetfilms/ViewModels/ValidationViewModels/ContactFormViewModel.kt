package com.example.jetfilms.ViewModels.ValidationViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Helpers.Validators.Results.ValidationResult
import com.example.jetfilms.Helpers.Validators.Validators.AccountScreen.ContactForm.FeedbackValidator
import com.example.jetfilms.Helpers.Validators.Validators.Registration.Username.UsernameValidator
import com.example.jetfilms.Models.Datastore.MyPreferences
import com.example.jetfilms.SEND_EMAIL_RESET_TIME_MINUTES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactFormViewModel @Inject constructor(
    private val myPreferences: MyPreferences
): ViewModel() {

    private val _usernameValidation: MutableStateFlow<ValidationResult> = MutableStateFlow(
        ValidationResult.NONE
    )
    val usernameValidation: StateFlow<ValidationResult> = _usernameValidation.asStateFlow()

    private val _feedBackValidation: MutableStateFlow<ValidationResult> = MutableStateFlow(
        ValidationResult.NONE
    )
    val feedBackValidation: StateFlow<ValidationResult> = _feedBackValidation.asStateFlow()

    private val _emailSentTime: MutableStateFlow<Long> = MutableStateFlow(0L)

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
        val timeToReset = DateFormats.getMinutesFromMillis( DateFormats.getCurrentDateMillis() - _emailSentTime.value)

        Log.d("time to reset",timeToReset.toString())

        return usernameValidator == ValidationResult.CORRECT &&
                feedbackValidator == ValidationResult.CORRECT &&
                timeToReset >= SEND_EMAIL_RESET_TIME_MINUTES
    }

    init {
        viewModelScope.launch {
            myPreferences.emailSentTimeFlow.collectLatest {
                _emailSentTime.emit(it)
            }
        }
    }

    fun setUsernameValidationResult(result: ValidationResult) = viewModelScope.launch {
        _usernameValidation.emit(result)
    }

    fun setFeedbackValidationResult(result: ValidationResult) = viewModelScope.launch {
        _feedBackValidation.emit(result)
    }

    fun setEmailSentTime() = viewModelScope.launch {
        myPreferences.setEmailSentTime(DateFormats.getCurrentDateMillis())
    }
}