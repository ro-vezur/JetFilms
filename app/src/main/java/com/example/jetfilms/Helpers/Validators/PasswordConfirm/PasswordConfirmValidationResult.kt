package com.example.jetfilms.Helpers.Validators.PasswordConfirm

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetfilms.ui.theme.correctColor
import com.example.jetfilms.ui.theme.errorColor

enum class PasswordConfirmValidationResult(val icon: ImageVector, val tint: Color) {
    EMPTY_FIELD(icon = Icons.Default.Error, tint = errorColor),
    NOT_MATCH(icon = Icons.Default.Error, tint = errorColor),
    CORRECT(icon = Icons.Default.TaskAlt, tint = correctColor),
    NONE(icon = Icons.Default.TaskAlt, tint = correctColor)
}