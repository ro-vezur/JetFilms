package com.example.jetfilms.View.Screens.Start

import com.example.jetfilms.View.Components.Text.Highlight
import com.example.jetfilms.View.Components.Text.HighlightedText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetfilms.View.Components.InputFields.TextInPutField.TextInputField
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.Helpers.Validators.Results.ValidationResult
import com.example.jetfilms.ViewModels.ValidationViewModels.LogInValidationViewModel
import com.example.jetfilms.blueHorizontalGradient
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.errorColor
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(
    stepsNavController: NavController,
    logIn: (email: String, password: String) -> Unit,
    logInValidationViewModel: LogInValidationViewModel = hiltViewModel(),
) {
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    val scope = rememberCoroutineScope()

    val passwordValidationResult by logInValidationViewModel.passwordValidation.collectAsStateWithLifecycle()

    var passwordText by remember{ mutableStateOf("") }
    val passwordError = passwordValidationResult == ValidationResult.ERROR
    var showPassword by remember{ mutableStateOf(false) }

    val emailValidationResult by logInValidationViewModel.emailValidation.collectAsStateWithLifecycle()
    var emailText by remember{ mutableStateOf("") }
    val emailError = emailValidationResult == ValidationResult.ERROR

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        TurnBackButton(
            onClick = { stepsNavController.navigate(WelcomeScreenRoute) },
            background = Color.LightGray.copy(0.6f),
            iconColor = Color.White,
            modifier = Modifier
                .padding(start = 16.sdp,top = 34.sdp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.sdp)
        ) {
            Text(
                text = "Log in",
                style = typography.titleLarge.copy(
                    fontSize = typography.titleLarge.fontSize * 1.15,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 38.sdp)
                    .fillMaxWidth(0.8f)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(17f.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 38.sdp)
                    .fillMaxWidth()
            ) {
                TextInputField(
                    text = emailText,
                    isError = emailError,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        emailText = value
                        logInValidationViewModel.setEmailValidationResult(ValidationResult.NONE)
                    },
                    placeHolder = "Email",
                    leadingIcon = Icons.Filled.Email,
                    trailingIcon = {
                        if(emailValidationResult != ValidationResult.NONE){
                            Icon(
                                imageVector = emailValidationResult.icon,
                                contentDescription = "check icon",
                                tint = emailValidationResult.tint,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                )

                TextInputField(
                    text = passwordText,
                    isError = passwordError,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        passwordText = value
                        logInValidationViewModel.setPasswordValidationResult(ValidationResult.NONE)
                    },
                    placeHolder = "Password",
                    leadingIcon = Icons.Filled.Lock,
                    trailingIcon = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.sdp)
                        ){
                            if(passwordValidationResult != ValidationResult.NONE){
                                Icon(
                                    imageVector = passwordValidationResult.icon,
                                    contentDescription = "check icon",
                                    tint = passwordValidationResult.tint,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(20.sdp)
                                )
                            }

                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "password",
                                tint = if(passwordError) errorColor else Color.LightGray.copy(0.9f) ,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                                    .clickable {
                                        showPassword = !showPassword
                                    }
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(15.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 58.sdp)
                    .fillMaxWidth()
            ) {

                TextButton(
                    onClick = {
                        scope.launch{
                            val valid = logInValidationViewModel.validation(
                                email = emailText,
                                password = passwordText
                            )

                            if (valid) {
                                logIn(emailText, passwordText)
                            }
                        }
                    },
                    text = "Log in",
                    corners = RoundedCornerShape(12.sdp),
                    gradient = blueHorizontalGradient,
                )

                Text(
                    text = "Forgot the password?",
                    style = typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = typography.bodySmall.fontSize / 1.02
                    )
                )
            }

            HighlightedText(
                text = "Don't have an account? Sign up",
                highlights = listOf(
                    Highlight(
                        text = "Sign up",
                        data = "",
                        onClick = { link ->
                            stepsNavController.navigate(SignUpScreenRoute)
                        }
                    ),
                ),
                textStyle = typography.bodySmall.copy(color = Color.LightGray.copy(0.77f)),
                highLightedTextStyle = typography.bodySmall.copy(
                    color = buttonsColor2,
                    fontSize = (typography.bodySmall.fontSize * 1.1)
                ),
            )
        }
    }
}