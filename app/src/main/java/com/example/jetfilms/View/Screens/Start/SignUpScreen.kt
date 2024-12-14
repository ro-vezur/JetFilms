package com.example.jetfilms.View.Screens.Start

import com.example.jetfilms.View.Components.Text.Highlight
import com.example.jetfilms.View.Components.Text.HighlightedText
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetfilms.View.Components.InputFields.TextInputField
import com.example.jetfilms.View.Components.Buttons.TextButton
import com.example.jetfilms.View.Components.Buttons.TurnBackButton
import com.example.jetfilms.BASE_BUTTON_HEIGHT
import com.example.jetfilms.Helpers.Validators.Results.EmailValidationResult
import com.example.jetfilms.Helpers.Validators.Results.PasswordValidationResult
import com.example.jetfilms.Helpers.Validators.Results.PasswordConfirmValidationResult
import com.example.jetfilms.Helpers.Validators.Results.UsernameValidationResult
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.ViewModels.SingUpValidationViewModel
import com.example.jetfilms.ViewModels.UserViewModel
import com.example.jetfilms.extensions.sdp
import com.example.jetfilms.ui.theme.buttonsColor1
import com.example.jetfilms.ui.theme.buttonsColor2
import com.example.jetfilms.ui.theme.errorColor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    stepsNavController: NavController,
    userViewModel: UserViewModel,
    signUpViewModel: SingUpValidationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    val scope = rememberCoroutineScope()

    val usernameValidationResult = signUpViewModel.usernameValidation.collectAsStateWithLifecycle()
    val emailValidationResult = signUpViewModel.emailValidation.collectAsStateWithLifecycle()
    val passwordValidationResult = signUpViewModel.passwordValidation.collectAsStateWithLifecycle()
    val passwordConfirmValidationResult = signUpViewModel.passwordConfirmValidation.collectAsStateWithLifecycle()

    val passwordError = passwordValidationResult.value != PasswordValidationResult.CORRECT &&
            passwordValidationResult.value != PasswordValidationResult.NONE
    val passwordConfirmError = passwordConfirmValidationResult.value != PasswordConfirmValidationResult.CORRECT &&
            passwordConfirmValidationResult.value != PasswordConfirmValidationResult.NONE

    val user = userViewModel.user.collectAsStateWithLifecycle()

    var firstName by remember{ mutableStateOf(user.value?.firstName?:"") }
    var lastName by remember{ mutableStateOf(user.value?.lastName?:"") }
    var emailText by remember{ mutableStateOf(user.value?.email?:"") }
    var passwordText by remember{ mutableStateOf(user.value?.password?:"") }
    var passwordConfirmText by remember{ mutableStateOf(user.value?.password?:"") }

    var showPassword by remember{ mutableStateOf(false) }

    Log.d("user",user.value.toString())

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TurnBackButton(
            onClick = { stepsNavController.navigate(WelcomeScreenRoute) },
            background = Color.LightGray.copy(0.6f),
            iconColor = Color.White,
            modifier = Modifier
                .padding(start = 16.sdp, top = 34.sdp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.sdp)
        ) {
            Text(
                text = "Sign up",
                style = typography.titleLarge.copy(
                    fontSize = typography.titleLarge.fontSize * 1.15,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 30.sdp)
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
                    text = firstName,
                    isError = usernameValidationResult.value != UsernameValidationResult.CORRECT &&
                            usernameValidationResult.value != UsernameValidationResult.NONE,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        firstName = value
                        signUpViewModel.setUsernameValidationResult(UsernameValidationResult.NONE)
                    },
                    placeHolder = "First Name",
                    leadingIcon = Icons.Filled.Person,
                    trailingIcon = {
                        if(usernameValidationResult.value != UsernameValidationResult.NONE){
                            Icon(
                                imageVector = usernameValidationResult.value.icon,
                                contentDescription = "check icon",
                                tint = usernameValidationResult.value.tint,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                            )
                        }
                    },
                    modifier = Modifier
                )

                TextInputField(
                    text = lastName,
                    isError = usernameValidationResult.value != UsernameValidationResult.CORRECT &&
                            usernameValidationResult.value != UsernameValidationResult.NONE,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        lastName = value
                        signUpViewModel.setUsernameValidationResult(UsernameValidationResult.NONE)
                    },
                    placeHolder = "Last Name",
                    leadingIcon = Icons.Filled.Person,
                    trailingIcon = {
                        if(usernameValidationResult.value != UsernameValidationResult.NONE){
                            Icon(
                                imageVector = usernameValidationResult.value.icon,
                                contentDescription = "check icon",
                                tint = usernameValidationResult.value.tint,
                                modifier = Modifier
                                    .padding(end = 11.sdp)
                                    .clip(CircleShape)
                                    .size(20.sdp)
                            )
                        }
                    },
                    modifier = Modifier
                )

                TextInputField(
                    text = emailText,
                    isError = emailValidationResult.value != EmailValidationResult.CORRECT &&
                    emailValidationResult.value != EmailValidationResult.NONE,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        emailText = value
                        signUpViewModel.setEmailValidationResult(EmailValidationResult.NONE)
                    },
                    placeHolder = "Email",
                    leadingIcon = Icons.Filled.Email,
                    trailingIcon = {
                        if(emailValidationResult.value != EmailValidationResult.NONE){
                            Icon(
                                imageVector = emailValidationResult.value.icon,
                                contentDescription = "check icon",
                                tint = emailValidationResult.value.tint,
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
                        signUpViewModel.setPasswordValidationResult(PasswordValidationResult.NONE)
                    },
                    placeHolder = "Password",
                    leadingIcon = Icons.Filled.Lock,
                    trailingIcon = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.sdp)
                        ){
                            if(passwordValidationResult.value != PasswordValidationResult.NONE){
                                Icon(
                                    imageVector = passwordValidationResult.value.icon,
                                    contentDescription = "check icon",
                                    tint = passwordValidationResult.value.tint,
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
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                )

                TextInputField(
                    text = passwordConfirmText,
                    isError = passwordConfirmError,
                    height = (BASE_BUTTON_HEIGHT + 1).sdp,
                    onTextChange = { value ->
                        passwordConfirmText = value
                        signUpViewModel.setPasswordConfirmValidationResult(PasswordConfirmValidationResult.NONE)
                    },
                    placeHolder = "Password Confirm",
                    leadingIcon = Icons.Filled.Lock,
                    trailingIcon = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.sdp)
                        ){
                            if(passwordConfirmValidationResult.value != PasswordConfirmValidationResult.NONE){
                                Icon(
                                    imageVector = passwordConfirmValidationResult.value.icon,
                                    contentDescription = "check icon",
                                    tint = passwordConfirmValidationResult.value.tint,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(20.sdp)
                                )
                            }

                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "password",
                                tint = if(passwordConfirmError) errorColor else Color.LightGray.copy(0.9f) ,
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
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                )
            }

            TextButton(
                onClick = {

                    scope.launch {
                        val valid = signUpViewModel.validation(
                            name = "$firstName $lastName",
                            email = emailText,
                            password = passwordText,
                            passwordConfirm = passwordConfirmText,
                        )

                        if (valid) {
                            val newUser = User(
                                id = "",
                                firstName = firstName,
                                lastName = lastName,
                                email = emailText,
                                password = passwordText,
                                recommendedMediaFormats = user.value?.recommendedMediaFormats
                                    ?: mutableListOf(),
                                recommendedMediaGenres = user.value?.recommendedMediaGenres
                                    ?: mutableListOf(),
                            )

                            userViewModel.setUser(newUser)
                            stepsNavController.navigate(SelectMediaFormatScreenRoute)
                        }
                    }

                },
                text = "Sign up",
                corners = RoundedCornerShape(12.sdp),
                gradient = Brush.horizontalGradient(
                    listOf(buttonsColor1, buttonsColor2)
                ),
                modifier = Modifier
                    .padding(bottom = 87.sdp)
            )

            HighlightedText(
                text = "Already have an account? Log in",
                highlights = listOf(
                    Highlight(
                        text = "Log in",
                        data = "",
                        onClick = {
                            stepsNavController.navigate(LogInScreenRoute)
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