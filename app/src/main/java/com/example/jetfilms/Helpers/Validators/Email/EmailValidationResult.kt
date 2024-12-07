package com.example.jetfilms.Helpers.Validators.Email

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetfilms.ui.theme.correctColor
import com.example.jetfilms.ui.theme.errorColor

enum class EmailValidationResult(val icon: ImageVector, val tint: Color = errorColor) {
    EMPTY_FIELD(icon = Icons.Default.Error, tint = errorColor),
    INCORRECT_FORMAT(icon = Icons.Default.Error, tint = errorColor),
    CORRECT(icon = Icons.Default.TaskAlt, tint = correctColor),
    NONE(icon = Icons.Default.TaskAlt, tint = correctColor)
}