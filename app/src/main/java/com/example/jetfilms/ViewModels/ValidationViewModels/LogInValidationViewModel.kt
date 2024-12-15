package com.example.jetfilms.ViewModels.ValidationViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Helpers.Validators.Results.EmailValidationResult
import com.example.jetfilms.Helpers.Validators.Results.PasswordValidationResult
import com.example.jetfilms.Helpers.Validators.Validators.Registration.Email.EmailValidator
import com.example.jetfilms.Helpers.Validators.Validators.Registration.Password.PasswordValidator
import com.example.jetfilms.Models.Repositories.Firebase.UsersCollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInValidationViewModel @Inject constructor(
    private val usersCollectionRepository: UsersCollectionRepository,
): ViewModel() {

    private val _emailValidation: MutableStateFlow<EmailValidationResult> = MutableStateFlow(
        EmailValidationResult.NONE
    )
    val emailValidation: StateFlow<EmailValidationResult> = _emailValidation.asStateFlow()

    private val _passwordValidation: MutableStateFlow<PasswordValidationResult> = MutableStateFlow(
        PasswordValidationResult.NONE
    )
    val passwordValidation: StateFlow<PasswordValidationResult> = _passwordValidation.asStateFlow()


    suspend fun validation(
        email: String,
        password: String,
    ): Boolean {
        val passwordValidator = PasswordValidator().invoke(
            password = password,
            checkIfPasswordNotMatches = !checkIfPasswordMatches(email, password)
        )
        val emailValidator = EmailValidator().invoke(
            email = email,
            checkIfEmailAlreadyRegistered = checkIfEmailIsRegistered(email)
        )

        viewModelScope.launch {
            _passwordValidation.emit(passwordValidator)
            _emailValidation.emit(emailValidator)
        }

        return passwordValidator == PasswordValidationResult.CORRECT &&
                emailValidator == EmailValidationResult.CORRECT
    }

    fun setEmailValidationResult(result: EmailValidationResult) = viewModelScope.launch {
        _emailValidation.emit(result)
    }

    fun setPasswordValidationResult(result: PasswordValidationResult) = viewModelScope.launch {
        _passwordValidation.emit(result)
    }

    private suspend fun checkIfEmailIsRegistered(emailToCheck: String): Boolean {
        return usersCollectionRepository.checkIfEmailIsRegistered(emailToCheck)
    }

    private suspend fun checkIfPasswordMatches(email: String, password: String): Boolean {
        return usersCollectionRepository.checkIfPasswordMatches(email, password)
    }
}