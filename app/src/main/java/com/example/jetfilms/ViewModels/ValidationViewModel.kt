package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Helpers.Validators.Email.EmailValidationResult
import com.example.jetfilms.Helpers.Validators.Email.EmailValidator
import com.example.jetfilms.Helpers.Validators.Password.PasswordValidationResult
import com.example.jetfilms.Helpers.Validators.Password.PasswordValidator
import com.example.jetfilms.Helpers.Validators.PasswordConfirm.PasswordConfirmValidationResult
import com.example.jetfilms.Helpers.Validators.PasswordConfirm.PasswordConfirmValidator
import com.example.jetfilms.Helpers.Validators.Username.UsernameValidationResult
import com.example.jetfilms.Helpers.Validators.Username.UsernameValidator
import com.example.jetfilms.Models.Firebase.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(): ViewModel() {

    private val _usernameValidation: MutableStateFlow<UsernameValidationResult> = MutableStateFlow(
        UsernameValidationResult.NONE
    )
    val usernameValidation: StateFlow<UsernameValidationResult> = _usernameValidation.asStateFlow()

    private val _emailValidation: MutableStateFlow<EmailValidationResult> = MutableStateFlow(
        EmailValidationResult.NONE
    )
    val emailValidation: StateFlow<EmailValidationResult> = _emailValidation.asStateFlow()

    private val _passwordValidation: MutableStateFlow<PasswordValidationResult> = MutableStateFlow(
        PasswordValidationResult.NONE
    )
    val passwordValidation: StateFlow<PasswordValidationResult> = _passwordValidation.asStateFlow()

    private val _passwordConfirmValidation: MutableStateFlow<PasswordConfirmValidationResult> = MutableStateFlow(
        PasswordConfirmValidationResult.NONE
    )
    val passwordConfirmValidation: StateFlow<PasswordConfirmValidationResult> = _passwordConfirmValidation.asStateFlow()

    fun validation(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String,
    ): Boolean {
        val passwordValidator = PasswordValidator().invoke(password)
        val emailValidator = EmailValidator().invoke(email)
        val usernameValidator = UsernameValidator().invoke(name)
        val passwordConfirmValidator = PasswordConfirmValidator().invoke(password,passwordConfirm)

        viewModelScope.launch {
            _passwordValidation.emit(passwordValidator)
            _emailValidation.emit(emailValidator)
            _usernameValidation.emit(usernameValidator)
            _passwordConfirmValidation.emit(passwordConfirmValidator)
        }

        val valid = passwordValidator == PasswordValidationResult.CORRECT &&
                emailValidator == EmailValidationResult.CORRECT &&
                usernameValidator == UsernameValidationResult.CORRECT &&
                passwordConfirmValidator == PasswordConfirmValidationResult.CORRECT

        return valid
    }

    fun setEmailValidationResult(result: EmailValidationResult) = viewModelScope.launch {
        _emailValidation.emit(result)
    }

    fun setUsernameValidationResult(result: UsernameValidationResult) = viewModelScope.launch {
        _usernameValidation.emit(result)
    }

    fun setPasswordValidationResult(result: PasswordValidationResult) = viewModelScope.launch {
        _passwordValidation.emit(result)
    }

    fun setPasswordConfirmValidationResult(result: PasswordConfirmValidationResult) = viewModelScope.launch {
        _passwordConfirmValidation.emit(result)
    }
}