package com.example.jetfilms.ViewModels.ValidationViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Helpers.Validators.Results.ValidationResult
import com.example.jetfilms.Helpers.Validators.Validators.AccountScreen.CheckOldPassword.CheckOldPassword
import com.example.jetfilms.Helpers.Validators.Validators.Registration.Email.EmailValidator
import com.example.jetfilms.Helpers.Validators.Validators.Registration.Password.PasswordValidator
import com.example.jetfilms.Helpers.Validators.Validators.Registration.Username.UsernameValidator
import com.example.jetfilms.Models.Repositories.Firebase.UsersCollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileValidationViewModel @Inject constructor(
    private val usersCollectionRepository: UsersCollectionRepository,
): ViewModel() {
    private val _usernameValidation: MutableStateFlow<ValidationResult> = MutableStateFlow(
        ValidationResult.NONE
    )
    val usernameValidation: StateFlow<ValidationResult> = _usernameValidation.asStateFlow()

    private val _emailValidation: MutableStateFlow<ValidationResult> = MutableStateFlow(
        ValidationResult.NONE
    )
    val emailValidation: StateFlow<ValidationResult> = _emailValidation.asStateFlow()

    private val _passwordValidation: MutableStateFlow<ValidationResult> = MutableStateFlow(
        ValidationResult.NONE
    )
    val passwordValidation: StateFlow<ValidationResult> = _passwordValidation.asStateFlow()

    private val _passwordConfirmValidation: MutableStateFlow<ValidationResult> = MutableStateFlow(
        ValidationResult.NONE
    )
    val passwordConfirmValidation: StateFlow<ValidationResult> = _passwordConfirmValidation.asStateFlow()

    suspend fun validateUsername(
        name: String,
        email: String,
    ): Boolean {
        val usernameValidator = UsernameValidator().invoke(
            username = name
        )

        val emailValidator = EmailValidator().invoke(
            email = email,
            checkIfEmailAlreadyRegistered = !checkIfEmailIsRegistered(email)
        )

        setUsernameValidationResult(usernameValidator)
        setEmailValidationResult(emailValidator)

        return emailValidator == ValidationResult.CORRECT &&
                usernameValidator == ValidationResult.CORRECT
    }

    suspend fun validatePassword(
        oldPassword: String,
        enteredPassword: String,
        newPassword: String,
    ): Boolean {
        val oldPasswordValidator = CheckOldPassword().invoke(
            oldPassword = oldPassword,
            enteredPassword = enteredPassword
        )

        val newPasswordValidator = PasswordValidator().invoke(
            password = newPassword,
            checkIfPasswordNotMatches = false
        )

        setOldPasswordValidationResult(oldPasswordValidator)
        setNewPasswordValidationResult(newPasswordValidator)

        return oldPasswordValidator == ValidationResult.CORRECT &&
                newPasswordValidator == ValidationResult.CORRECT
    }

    fun setEmailValidationResult(result: ValidationResult) = viewModelScope.launch {
        _emailValidation.emit(result)
    }

    fun setUsernameValidationResult(result: ValidationResult) = viewModelScope.launch {
        _usernameValidation.emit(result)
    }

    fun setOldPasswordValidationResult(result: ValidationResult) = viewModelScope.launch {
        _passwordValidation.emit(result)
    }

    fun setNewPasswordValidationResult(result: ValidationResult) = viewModelScope.launch {
        _passwordConfirmValidation.emit(result)
    }

    private suspend fun checkIfEmailIsRegistered(emailToCheck: String): Boolean {
        return usersCollectionRepository.checkIfEmailIsRegistered(emailToCheck)
    }
}