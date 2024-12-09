package com.example.jetfilms.Helpers.Validators.Validators.Password

import com.example.jetfilms.Helpers.Validators.Results.PasswordValidationResult

class PasswordValidator {
    suspend operator fun invoke(password: String, additionalValidators: suspend () -> Boolean = {false}): PasswordValidationResult {
        return if (password.length < 8) PasswordValidationResult.ERROR
        else if (password.count(Char::isUpperCase) == 0) PasswordValidationResult.ERROR
        else if (!password.contains("[0-9]".toRegex())) PasswordValidationResult.ERROR
        else if(additionalValidators())  PasswordValidationResult.ERROR
        else PasswordValidationResult.CORRECT
    }
}