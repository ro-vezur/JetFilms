package com.example.jetfilms.Helpers.Validators.Password

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetfilms.ui.theme.correctColor
import com.example.jetfilms.ui.theme.errorColor

enum class PasswordValidationResult(val icon: ImageVector, val tint: Color) {
    NOT_LONG_ENOUGH(icon = Icons.Default.Error, tint = errorColor),
    NOT_ENOUGH_DIGITS(icon = Icons.Default.Error, tint = errorColor),
    NOT_ENOUGH_UPPERCASE(icon = Icons.Default.Error, tint = errorColor),
    CORRECT(icon = Icons.Default.TaskAlt, tint = correctColor),
    NONE(icon = Icons.Default.TaskAlt, tint = correctColor)
}