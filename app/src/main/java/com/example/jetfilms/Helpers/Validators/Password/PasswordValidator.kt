package com.example.jetfilms.Helpers.Validators.Password

class PasswordValidator {
    operator fun invoke(password: String): PasswordValidationResult {
        return if (password.length < 8) PasswordValidationResult.NOT_LONG_ENOUGH
        else if (password.count(Char::isUpperCase) == 0) PasswordValidationResult.NOT_ENOUGH_UPPERCASE
        else if (!password.contains("[0-9]".toRegex())) PasswordValidationResult.NOT_ENOUGH_DIGITS
        else PasswordValidationResult.CORRECT
    }
}